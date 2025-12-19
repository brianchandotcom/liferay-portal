/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.pricing.resource.v2_0.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.PriceList;
import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.PriceListAccount;
import com.liferay.headless.commerce.admin.pricing.client.resource.v2_0.PriceListResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Arrays;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zoltán Takács
 */
@RunWith(Arquillian.class)
public class PriceListResourceTest extends BasePriceListResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			testGroup.getCompanyId());

		_user = UserTestUtil.addUser(testCompany);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());

		_accountEntry = _accountEntryLocalService.addAccountEntry(
			StringPool.BLANK, _user.getUserId(), 0,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			RandomTestUtil.randomString() + "@liferay.com", null, null,
			"business", 1, _serviceContext);

		_commerceCatalog = CommerceTestUtil.addCommerceCatalog(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId(), _commerceCurrency.getCode());
	}

	@Ignore
	@Override
	@Test
	public void testGetPriceListsPageWithFilterDateTimeEquals()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetPriceListsPageWithFilterStringContains()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetPriceListsPageWithFilterStringEquals() throws Exception {
		super.testGetPriceListsPageWithFilterStringEquals();
	}

	@Ignore
	@Override
	@Test
	public void testGetPriceListsPageWithFilterStringStartsWith()
		throws Exception {

		super.testGetPriceListsPageWithFilterStringStartsWith();
	}

	@Ignore
	@Override
	@Test
	public void testGetPriceListsPageWithSortString() throws Exception {
		super.testGetPriceListsPageWithSortString();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetPriceList() throws Exception {
		super.testGraphQLGetPriceList();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetPriceListByExternalReferenceCode()
		throws Exception {

		super.testGraphQLGetPriceListByExternalReferenceCode();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetPriceListByExternalReferenceCodeNotFound()
		throws Exception {

		super.testGraphQLGetPriceListByExternalReferenceCodeNotFound();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetPriceListNotFound() throws Exception {
		super.testGraphQLGetPriceListNotFound();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetPriceListsPage() throws Exception {
		super.testGraphQLGetPriceListsPage();
	}

	@Override
	@Test
	public void testPostPriceList() throws Exception {
		super.testPostPriceList();

		_testPostPriceListWithSamePriceListAccount();
	}

	@Override
	protected PriceList randomPriceList() throws Exception {
		long time = System.currentTimeMillis();

		return new PriceList() {
			{
				active = Boolean.TRUE;
				author = StringUtil.toLowerCase(RandomTestUtil.randomString());
				catalogBasePriceList = Boolean.FALSE;
				catalogExternalReferenceCode = _commerceCatalog.getExternalReferenceCode();
				catalogId = _commerceCatalog.getCommerceCatalogId();
				catalogName = _commerceCatalog.getName();
				createDate = RandomTestUtil.nextDate();
				currencyCode = _commerceCurrency.getCode();
				currencyExternalReferenceCode =
					_commerceCurrency.getExternalReferenceCode();
				currencyId = _commerceCurrency.getCommerceCurrencyId();
				displayDate = new Date(time - Time.HOUR);
				expirationDate = new Date(time + Time.DAY);
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				netPrice = RandomTestUtil.randomBoolean();
				neverExpire = RandomTestUtil.randomBoolean();
				parentPriceListId = 0L;
				priority = RandomTestUtil.randomDouble();
				type = Type.create(CommercePriceListConstants.TYPE_PRICE_LIST);
			}
		};
	}

	@Override
	protected PriceList testDeletePriceList_addPriceList() throws Exception {
		return priceListResource.postPriceList(randomPriceList());
	}

	@Override
	protected PriceList
			testDeletePriceListByExternalReferenceCode_addPriceList()
		throws Exception {

		return priceListResource.postPriceList(randomPriceList());
	}

	@Override
	protected PriceList testGetPriceList_addPriceList() throws Exception {
		return priceListResource.postPriceList(randomPriceList());
	}

	@Override
	protected PriceList testGetPriceListByExternalReferenceCode_addPriceList()
		throws Exception {

		return priceListResource.postPriceList(randomPriceList());
	}

	@Override
	protected PriceList testGetPriceListsPage_addPriceList(PriceList priceList)
		throws Exception {

		return priceListResource.postPriceList(priceList);
	}

	@Override
	protected PriceList testGraphQLPriceList_addPriceList() throws Exception {
		return priceListResource.postPriceList(randomPriceList());
	}

	@Override
	protected PriceList testPatchPriceList_addPriceList() throws Exception {
		return priceListResource.postPriceList(randomPriceList());
	}

	@Override
	protected PriceList testPatchPriceListByExternalReferenceCode_addPriceList()
		throws Exception {

		return priceListResource.postPriceList(randomPriceList());
	}

	@Override
	protected PriceList testPostPriceList_addPriceList(PriceList priceList)
		throws Exception {

		return priceListResource.postPriceList(priceList);
	}

	@Override
	protected PriceList testPutPriceListByExternalReferenceCode_addPriceList()
		throws Exception {

		return priceListResource.postPriceList(randomPriceList());
	}

	private PriceListAccount _createPriceListAccount(PriceList priceList)
		throws Exception {

		return new PriceListAccount() {
			{
				accountExternalReferenceCode =
					_accountEntry.getExternalReferenceCode();
				accountId = _accountEntry.getAccountEntryId();
				priceListExternalReferenceCode =
					priceList.getExternalReferenceCode();
				priceListId = priceList.getId();
			}
		};
	}

	private void _testPostPriceListWithSamePriceListAccount() throws Exception {
		User omniadminUser = UserTestUtil.addOmniadminUser();
		String password = RandomTestUtil.randomString();

		_userLocalService.updatePassword(
			omniadminUser.getUserId(), password, password, false, true);

		PriceListResource priceListResource = PriceListResource.builder(
		).authentication(
			omniadminUser.getEmailAddress(), password
		).locale(
			LocaleUtil.getDefault()
		).parameters(
			"nestedFields", "priceListAccounts"
		).build();

		PriceList priceList = randomPriceList();

		priceList.setPriceListAccounts(
			new PriceListAccount[] {_createPriceListAccount(priceList)});

		PriceList expectedPriceList = priceListResource.getPriceList(
			testPostPriceList_addPriceList(
				priceList
			).getId());
		PriceList actualPriceList = priceListResource.getPriceList(
			testPostPriceList_addPriceList(
				priceList
			).getId());

		assertEquals(expectedPriceList, actualPriceList);

		PriceListAccount[] expectedPriceListAccounts =
			expectedPriceList.getPriceListAccounts();

		Assert.assertEquals(
			Arrays.toString(expectedPriceListAccounts), 1,
			expectedPriceListAccounts.length);

		PriceListAccount[] actualPriceListAccounts =
			actualPriceList.getPriceListAccounts();

		Assert.assertEquals(
			Arrays.toString(actualPriceListAccounts), 1,
			actualPriceListAccounts.length);

		PriceListAccount expectedPriceListAccount =
			expectedPriceListAccounts[0];
		PriceListAccount actualPriceListAccount = expectedPriceListAccounts[0];

		Assert.assertEquals(
			actualPriceListAccount.toString(),
			expectedPriceListAccount.getAccountId(),
			actualPriceListAccount.getAccountId());
		Assert.assertEquals(
			actualPriceListAccount.toString(),
			expectedPriceListAccount.getPriceListId(),
			actualPriceListAccount.getPriceListId());
	}

	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	private CommerceCatalog _commerceCatalog;
	private CommerceCurrency _commerceCurrency;
	private ServiceContext _serviceContext;
	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}