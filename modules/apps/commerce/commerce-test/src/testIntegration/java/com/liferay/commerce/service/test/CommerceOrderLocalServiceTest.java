/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.constants.CommerceAddressConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelLocalService;
import com.liferay.commerce.payment.test.util.TestCommercePaymentMethod;
import com.liferay.commerce.product.constants.CommerceChannelAccountEntryRelConstants;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.service.CommerceAddressLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceShippingMethodLocalService;
import com.liferay.commerce.service.CommerceShippingOptionAccountEntryRelService;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionLocalService;
import com.liferay.commerce.term.constants.CommerceTermEntryConstants;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.service.CommerceTermEntryLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;

import java.util.Collections;
import java.util.List;

import org.frutilla.FrutillaRule;

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
public class CommerceOrderLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());
	}

	@Test
	public void testAddCommerceOrder() throws Exception {
		_accountEntry = CommerceAccountTestUtil.addBusinessAccountEntry(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString() + "@liferay.com",
			RandomTestUtil.randomString(), new long[] {_user.getUserId()}, null,
			_serviceContext);

		Country country = _countryLocalService.addCountry(
			"ZZ", "ZZZ", true, true, null, RandomTestUtil.randomString(), "000",
			RandomTestUtil.randomDouble(), true, false, false, _serviceContext);

		Region region = _regionLocalService.addRegion(
			country.getCountryId(), true, RandomTestUtil.randomString(),
			RandomTestUtil.randomDouble(), "ZZ", _serviceContext);

		CommerceAddress commerceAddress =
			_commerceAddressLocalService.addCommerceAddress(
				StringPool.BLANK, AccountEntry.class.getName(),
				_accountEntry.getAccountEntryId(), country.getCountryId(),
				region.getRegionId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				StringPool.BLANK,
				CommerceAddressConstants.ADDRESS_TYPE_BILLING_AND_SHIPPING,
				String.valueOf(30133), _serviceContext);

		_commerceChannelAccountEntryRelLocalService.
			addCommerceChannelAccountEntryRel(
				_user.getUserId(), _accountEntry.getAccountEntryId(),
				Address.class.getName(), commerceAddress.getCommerceAddressId(),
				_commerceChannel.getCommerceChannelId(), true, 0,
				CommerceChannelAccountEntryRelConstants.TYPE_BILLING_ADDRESS);
		_commerceChannelAccountEntryRelLocalService.
			addCommerceChannelAccountEntryRel(
				_user.getUserId(), _accountEntry.getAccountEntryId(),
				Address.class.getName(), commerceAddress.getCommerceAddressId(),
				_commerceChannel.getCommerceChannelId(), true, 0,
				CommerceChannelAccountEntryRelConstants.TYPE_SHIPPING_ADDRESS);

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
			_commercePaymentMethodGroupRelLocalService.
				addCommercePaymentMethodGroupRel(
					_user.getUserId(), _commerceChannel.getGroupId(),
					RandomTestUtil.randomLocaleStringMap(),
					RandomTestUtil.randomLocaleStringMap(), true, null,
					TestCommercePaymentMethod.KEY, 99, null);

		_commerceChannelAccountEntryRelLocalService.
			addCommerceChannelAccountEntryRel(
				_user.getUserId(), _accountEntry.getAccountEntryId(),
				CommercePaymentMethodGroupRel.class.getName(),
				commercePaymentMethodGroupRel.
					getCommercePaymentMethodGroupRelId(),
				_commerceChannel.getCommerceChannelId(), true, 0,
				CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT);

		CommerceShippingMethod commerceShippingMethod =
			_commerceShippingMethodLocalService.addCommerceShippingMethod(
				_user.getUserId(), _commerceChannel.getGroupId(),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()),
				true, "fixed", null, 1, RandomTestUtil.randomString());

		CommerceShippingFixedOption commerceShippingFixedOption =
			_commerceShippingFixedOptionLocalService.
				addCommerceShippingFixedOption(
					_user.getUserId(), _commerceChannel.getGroupId(),
					commerceShippingMethod.getCommerceShippingMethodId(),
					BigDecimal.valueOf(RandomTestUtil.nextDouble()),
					RandomTestUtil.randomLocaleStringMap(),
					RandomTestUtil.randomString(),
					Collections.singletonMap(
						LocaleUtil.US, RandomTestUtil.randomString()),
					RandomTestUtil.nextDouble());

		_commerceShippingOptionAccountEntryRelService.
			addCommerceShippingOptionAccountEntryRel(
				_accountEntry.getAccountEntryId(),
				_commerceChannel.getCommerceChannelId(),
				commerceShippingMethod.getEngineKey(),
				commerceShippingFixedOption.getKey());

		CommerceTermEntry commerceDeliveryTerm =
			_commerceTermEntryLocalService.addCommerceTermEntry(
				RandomTestUtil.randomString(), _user.getUserId(), true,
				Collections.singletonMap(
					LocaleUtil.getSiteDefault(), RandomTestUtil.randomString()),
				1, 1, 2022, 12, 0, 0, 0, 0, 0, 0, true,
				Collections.singletonMap(
					LocaleUtil.getSiteDefault(), RandomTestUtil.randomString()),
				RandomTestUtil.randomString(), 1000,
				CommerceTermEntryConstants.TYPE_DELIVERY_TERMS, null,
				_serviceContext);

		_commerceChannelAccountEntryRelLocalService.
			addCommerceChannelAccountEntryRel(
				_user.getUserId(), _accountEntry.getAccountEntryId(),
				CommerceTermEntry.class.getName(),
				commerceDeliveryTerm.getCommerceTermEntryId(),
				_commerceChannel.getCommerceChannelId(), true, 0,
				CommerceChannelAccountEntryRelConstants.TYPE_DELIVERY_TERM);

		CommerceTermEntry commercePaymentTerm =
			_commerceTermEntryLocalService.addCommerceTermEntry(
				RandomTestUtil.randomString(), _user.getUserId(), true,
				Collections.singletonMap(
					LocaleUtil.getSiteDefault(), RandomTestUtil.randomString()),
				1, 1, 2022, 12, 0, 0, 0, 0, 0, 0, true,
				Collections.singletonMap(
					LocaleUtil.getSiteDefault(), RandomTestUtil.randomString()),
				RandomTestUtil.randomString(), 1000,
				CommerceTermEntryConstants.TYPE_PAYMENT_TERMS, null,
				_serviceContext);

		_commerceChannelAccountEntryRelLocalService.
			addCommerceChannelAccountEntryRel(
				_user.getUserId(), _accountEntry.getAccountEntryId(),
				CommerceTermEntry.class.getName(),
				commercePaymentTerm.getCommerceTermEntryId(),
				_commerceChannel.getCommerceChannelId(), true, 0,
				CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT_TERM);

		CommerceOrder commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _user.getUserId(),
			_accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId());

		Assert.assertEquals(
			commerceAddress.getCommerceAddressId(),
			commerceOrder.getBillingAddressId());
		Assert.assertEquals(0, commerceOrder.getCommerceShippingMethodId());
		Assert.assertEquals(0, commerceOrder.getDeliveryCommerceTermEntryId());
		Assert.assertEquals(
			commercePaymentTerm.getCommerceTermEntryId(),
			commerceOrder.getPaymentCommerceTermEntryId());
		Assert.assertEquals(
			commerceAddress.getCommerceAddressId(),
			commerceOrder.getShippingAddressId());
		Assert.assertEquals(
			commercePaymentMethodGroupRel.getPaymentIntegrationKey(),
			commerceOrder.getCommercePaymentMethodKey());
		Assert.assertEquals(
			StringPool.BLANK, commerceOrder.getShippingOptionName());

		CPInstance cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			_group.getGroupId(), BigDecimal.valueOf(34.90));

		CommerceTestUtil.addCommerceOrderItem(
			commerceOrder.getCommerceOrderId(), cpInstance.getCPInstanceId(),
			BigDecimal.ONE);

		commerceOrder = _commerceOrderLocalService.getCommerceOrder(
			commerceOrder.getCommerceOrderId());

		Assert.assertEquals(
			commerceShippingMethod.getCommerceShippingMethodId(),
			commerceOrder.getCommerceShippingMethodId());
		Assert.assertEquals(
			commerceDeliveryTerm.getCommerceTermEntryId(),
			commerceOrder.getDeliveryCommerceTermEntryId());
		Assert.assertEquals(
			commerceShippingFixedOption.getKey(),
			commerceOrder.getShippingOptionName());
	}

	@Test
	public void testAddCommerceOrderAttachment() throws Exception {
		frutillaRule.scenario(
			"Add an attachment to an order"
		).given(
			"An order"
		).when(
			"I add an attachment"
		).then(
			"I should be able to retrieve it"
		);

		_accountEntry = CommerceAccountTestUtil.addPersonAccountEntry(
			_user.getUserId(),
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId()));

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.addCommerceOrder(
				_user.getUserId(), _commerceChannel.getGroupId(),
				_accountEntry.getAccountEntryId(), _commerceCurrency.getCode(),
				0);

		Class<?> clazz = getClass();

		FileEntry fileEntry1 =
			_commerceOrderLocalService.addAttachmentFileEntry(
				RandomTestUtil.randomString(), _user.getUserId(),
				commerceOrder.getCommerceOrderId(),
				RandomTestUtil.randomString(),
				clazz.getResourceAsStream("dependencies/attachment.txt"));

		LocalRepository localRepository = commerceOrder.getLocalRepository();

		Assert.assertNotNull(localRepository);

		Folder folder = commerceOrder.getFolder(localRepository);

		Assert.assertNotNull(folder);

		List<FileEntry> attachmentFileEntries =
			commerceOrder.getAttachmentFileEntries(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			attachmentFileEntries.toString(), 1, attachmentFileEntries.size());

		FileEntry fileEntry2 = attachmentFileEntries.get(0);

		Assert.assertEquals(
			fileEntry1.getExternalReferenceCode(),
			fileEntry2.getExternalReferenceCode());
		Assert.assertEquals(
			fileEntry1.getFileEntryId(), fileEntry2.getFileEntryId());
		Assert.assertEquals(folder.getFolderId(), fileEntry2.getFolderId());
	}

	@Test
	public void testDeleteCommerceOrderAttachment() throws Exception {
		frutillaRule.scenario(
			"Delete an attachment from an order"
		).given(
			"An order with an attachment"
		).when(
			"I delete the attachment"
		).then(
			"The file entry does not exist anymore"
		);

		_accountEntry = CommerceAccountTestUtil.addPersonAccountEntry(
			_user.getUserId(),
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId()));

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.addCommerceOrder(
				_user.getUserId(), _commerceChannel.getGroupId(),
				_accountEntry.getAccountEntryId(), _commerceCurrency.getCode(),
				0);

		Class<?> clazz = getClass();

		FileEntry fileEntry = _commerceOrderLocalService.addAttachmentFileEntry(
			RandomTestUtil.randomString(), _user.getUserId(),
			commerceOrder.getCommerceOrderId(), RandomTestUtil.randomString(),
			clazz.getResourceAsStream("dependencies/attachment.txt"));

		LocalRepository localRepository = commerceOrder.getLocalRepository();

		Assert.assertNotNull(localRepository);

		_commerceOrderLocalService.deleteAttachmentFileEntry(
			fileEntry.getFileEntryId(), commerceOrder.getCommerceOrderId());

		List<FileEntry> attachmentFileEntries =
			commerceOrder.getAttachmentFileEntries(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			attachmentFileEntries.toString(), 0, attachmentFileEntries.size());

		Assert.assertNull(
			localRepository.fetchFileEntryByExternalReferenceCode(
				fileEntry.getExternalReferenceCode()));
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private AccountEntry _accountEntry;

	@Inject
	private CommerceAddressLocalService _commerceAddressLocalService;

	private CommerceChannel _commerceChannel;

	@Inject
	private CommerceChannelAccountEntryRelLocalService
		_commerceChannelAccountEntryRelLocalService;

	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Inject
	private CommercePaymentMethodGroupRelLocalService
		_commercePaymentMethodGroupRelLocalService;

	@Inject
	private CommerceShippingFixedOptionLocalService
		_commerceShippingFixedOptionLocalService;

	@Inject
	private CommerceShippingMethodLocalService
		_commerceShippingMethodLocalService;

	@Inject
	private CommerceShippingOptionAccountEntryRelService
		_commerceShippingOptionAccountEntryRelService;

	@Inject
	private CommerceTermEntryLocalService _commerceTermEntryLocalService;

	@Inject
	private CountryLocalService _countryLocalService;

	private Group _group;

	@Inject
	private RegionLocalService _regionLocalService;

	private ServiceContext _serviceContext;
	private User _user;

}