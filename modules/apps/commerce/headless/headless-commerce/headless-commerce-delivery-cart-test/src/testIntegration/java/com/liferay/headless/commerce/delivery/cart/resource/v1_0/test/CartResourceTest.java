/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.cart.resource.v1_0.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.headless.commerce.delivery.cart.client.dto.v1_0.Cart;
import com.liferay.headless.commerce.delivery.cart.client.dto.v1_0.CouponCode;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Andrea Sbarra
 */
@RunWith(Arquillian.class)
public class CartResourceTest extends BaseCartResourceTestCase {

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

		_user = UserTestUtil.addUser(testCompany);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());

		_accountEntry = CommerceAccountTestUtil.addBusinessAccountEntry(
			_serviceContext.getUserId(), "Test Business Account", null, null,
			new long[] {_user.getUserId()}, null, _serviceContext);

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			testGroup.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			testGroup.getGroupId(), _commerceCurrency.getCode());

		_country = _countryLocalService.addCountry(
			"XY", "XYZ", true, true, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.nextDouble(), true, true, false, _serviceContext);

		_region = _regionLocalService.addRegion(
			_country.getCountryId(), true, RandomTestUtil.randomString(),
			RandomTestUtil.nextDouble(), RandomTestUtil.randomString(),
			_serviceContext);
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		List<CommerceOrder> commerceOrders =
			_commerceOrderLocalService.getCommerceOrders(
				_commerceChannel.getGroupId(),
				_accountEntry.getAccountEntryId(), -1, -1, null);

		for (CommerceOrder commerceOrder : commerceOrders) {
			_commerceOrderLocalService.deleteCommerceOrder(
				commerceOrder.getCommerceOrderId());
		}

		if (_accountEntry != null) {
			_accountEntryLocalService.deleteAccountEntry(_accountEntry);
		}
	}

	@Override
	@Test
	public void testDeleteCart() throws Exception {
		Cart cart = testDeleteCart_addCart();

		assertHttpResponseStatusCode(
			204, cartResource.deleteCartHttpResponse(cart.getId()));
	}

	@Override
	@Test
	public void testGetCartByExternalReferenceCodePaymentUrl()
		throws Exception {

		Cart cart = randomCart();

		String callbackURL = RandomTestUtil.randomString();

		Assert.assertEquals(
			StringBundler.concat(
				"http://localhost:8080/o/commerce-payment?groupId=",
				_commerceChannel.getGroupId(), "&nextStep=", callbackURL,
				"&uuid=", cart.getOrderUUID()),
			cartResource.getCartByExternalReferenceCodePaymentUrl(
				cart.getExternalReferenceCode(), callbackURL));
	}

	@Override
	@Test
	public void testGetCartPaymentURL() throws Exception {
		Cart cart = randomCart();

		String callbackURL = RandomTestUtil.randomString();

		Assert.assertEquals(
			StringBundler.concat(
				"http://localhost:8080/o/commerce-payment?groupId=",
				_commerceChannel.getGroupId(), "&nextStep=", callbackURL,
				"&uuid=", cart.getOrderUUID()),
			cartResource.getCartPaymentURL(cart.getId(), callbackURL));
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLDeleteCart() throws Exception {
	}

	@Override
	@Test
	public void testPatchCart() throws Exception {
		super.testPatchCart();

		_testPatchCartWithMoreExternalReferenceCodes();
	}

	@Override
	@Test
	public void testPatchCartByExternalReferenceCode() throws Exception {
		super.testPatchCartByExternalReferenceCode();

		_testPatchCartByExternalReferenceCodeWithMoreExternalReferenceCodes();
	}

	@Override
	@Test
	public void testPostChannelCart() throws Exception {
		super.testPostChannelCart();

		_testPostChannelCartWithMoreExternalReferenceCodes();
	}

	@Override
	@Test
	public void testPostChannelCartByExternalReferenceCode() throws Exception {
		super.testPostChannelCartByExternalReferenceCode();

		_testPostChannelCartByExternalReferenceCodeWithMoreExternalReferenceCodes();
	}

	@Override
	@Test
	public void testPutCart() throws Exception {
		super.testPutCart();

		_testPutCartWithMoreExternalReferenceCodes();
	}

	@Override
	@Test
	public void testPutCartByExternalReferenceCode() throws Exception {
		super.testPutCartByExternalReferenceCode();

		_testPutCartByExternalReferenceCodeWithMoreExternalReferenceCodes();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"account", "accountId", "billingAddressId", "couponCode",
			"orderTypeId", "paymentStatus", "shippingAddressId", "status"
		};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {
			"account", "accountId", "author", "name", "orderDate", "orderId",
			"orderType", "purchaseOrderNumber"
		};
	}

	@Override
	protected Cart randomCart() throws Exception {
		CommerceOrder commerceOrder = _getCommerceOrder();

		return new Cart() {
			{
				account = commerceOrder.getCommerceAccountName();
				accountId = commerceOrder.getCommerceAccountId();
				billingAddressId = commerceOrder.getBillingAddressId();
				couponCode = commerceOrder.getCouponCode();
				currencyCode = _commerceCurrency.getCode();
				externalReferenceCode =
					commerceOrder.getExternalReferenceCode();
				id = commerceOrder.getCommerceOrderId();
				name = commerceOrder.getName();
				orderTypeId = commerceOrder.getCommerceOrderTypeId();
				orderUUID = commerceOrder.getUuid();
				paymentStatus = commerceOrder.getPaymentStatus();
				shippingAddressId = commerceOrder.getShippingAddressId();
				status = WorkflowConstants.getStatusLabel(
					commerceOrder.getStatus());
			}
		};
	}

	@Override
	protected Cart testDeleteCart_addCart() throws Exception {
		Cart cart = randomCart();

		return cartResource.postCartCheckout(cart.getId());
	}

	@Override
	protected Cart testDeleteCartByExternalReferenceCode_addCart()
		throws Exception {

		Cart cart = randomCart();

		return cartResource.postCartByExternalReferenceCodeCheckout(
			cart.getExternalReferenceCode());
	}

	@Override
	protected Cart testGetCart_addCart() throws Exception {
		Cart cart = randomCart();

		return cartResource.postCartCheckout(cart.getId());
	}

	@Override
	protected Cart testGetCartByExternalReferenceCode_addCart()
		throws Exception {

		Cart cart = randomCart();

		return cartResource.postCartByExternalReferenceCodeCheckout(
			cart.getExternalReferenceCode());
	}

	@Override
	protected Cart testGetChannelAccountCartsPage_addCart(
			Long accountId, Long channelId, Cart cart)
		throws Exception {

		return cartResource.postCartCheckout(cart.getId());
	}

	@Override
	protected Long testGetChannelAccountCartsPage_getAccountId()
		throws Exception {

		return _accountEntry.getAccountEntryId();
	}

	@Override
	protected Long testGetChannelAccountCartsPage_getChannelId()
		throws Exception {

		return _commerceChannel.getCommerceChannelId();
	}

	@Override
	protected Cart
			testGetChannelByExternalReferenceCodeChannelExternalReferenceCodeAccountByExternalReferenceCodeAccountExternalReferenceCodeCartsPage_addCart(
				String accountExternalReferenceCode,
				String channelExternalReferenceCode, Cart cart)
		throws Exception {

		return cartResource.postCartByExternalReferenceCodeCheckout(
			cart.getExternalReferenceCode());
	}

	@Override
	protected String
			testGetChannelByExternalReferenceCodeChannelExternalReferenceCodeAccountByExternalReferenceCodeAccountExternalReferenceCodeCartsPage_getAccountExternalReferenceCode()
		throws Exception {

		return _accountEntry.getExternalReferenceCode();
	}

	@Override
	protected String
			testGetChannelByExternalReferenceCodeChannelExternalReferenceCodeAccountByExternalReferenceCodeAccountExternalReferenceCodeCartsPage_getChannelExternalReferenceCode()
		throws Exception {

		return _commerceChannel.getExternalReferenceCode();
	}

	@Override
	protected Cart testGetChannelCartsPage_addCart(Long channelId, Cart cart)
		throws Exception {

		return cartResource.postCartCheckout(
			_commerceOrder.getCommerceOrderId());
	}

	@Override
	protected Long testGetChannelCartsPage_getChannelId() throws Exception {
		return _commerceChannel.getCommerceChannelId();
	}

	@Override
	protected Cart testGraphQLCart_addCart() throws Exception {
		Cart cart = randomCart();

		return cartResource.postCartCheckout(cart.getId());
	}

	@Override
	protected Cart testPatchCart_addCart() throws Exception {
		return randomCart();
	}

	@Override
	protected Cart testPatchCartByExternalReferenceCode_addCart()
		throws Exception {

		return randomCart();
	}

	@Override
	protected Cart testPostCartByExternalReferenceCodeCheckout_addCart(
			Cart cart)
		throws Exception {

		return cartResource.postCartByExternalReferenceCodeCheckout(
			cart.getExternalReferenceCode());
	}

	@Override
	protected Cart testPostCartByExternalReferenceCodeCouponCode_addCart(
			Cart cart)
		throws Exception {

		CouponCode couponCode = new CouponCode() {
			{
				code = cart.getCouponCode();
			}
		};

		return cartResource.postCartByExternalReferenceCodeCouponCode(
			cart.getExternalReferenceCode(), couponCode);
	}

	@Override
	protected Cart testPostCartCheckout_addCart(Cart cart) throws Exception {
		return cartResource.postCartCheckout(cart.getId());
	}

	@Override
	protected Cart testPostCartCouponCode_addCart(Cart cart) throws Exception {
		CouponCode couponCode = new CouponCode() {
			{
				code = cart.getCouponCode();
			}
		};

		return cartResource.postCartCouponCode(cart.getId(), couponCode);
	}

	@Override
	protected Cart testPostChannelCartByExternalReferenceCode_addCart(Cart cart)
		throws Exception {

		return cartResource.postCartByExternalReferenceCodeCheckout(
			cart.getExternalReferenceCode());
	}

	@Override
	protected Cart testPutCart_addCart() throws Exception {
		Cart cart = randomCart();

		return cartResource.postCartCheckout(cart.getId());
	}

	@Override
	protected Cart testPutCartByExternalReferenceCode_addCart()
		throws Exception {

		Cart cart = randomCart();

		return cartResource.postCartByExternalReferenceCodeCheckout(
			cart.getExternalReferenceCode());
	}

	private CommerceOrder _getCommerceOrder() throws Exception {
		_commerceOrder = _commerceOrderLocalService.addCommerceOrder(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_accountEntry.getAccountEntryId(), _commerceCurrency.getCode(), 0);

		return _commerceOrder;
	}

	private void _testPatchCartByExternalReferenceCodeWithMoreExternalReferenceCodes()
		throws Exception {

		Cart postCart = cartResource.postChannelCart(
			_commerceChannel.getCommerceChannelId(), randomCart());

		Cart randomPatchCart = randomPatchCart();

		Address randomAddress = _addressLocalService.addAddress(
			RandomTestUtil.randomString(), _user.getUserId(),
			AccountEntry.class.getName(), _accountEntry.getAccountEntryId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), _region.getRegionId(),
			_country.getCountryId(), 0, false, true,
			RandomTestUtil.randomString(), _serviceContext);

		randomPatchCart.setBillingAddressExternalReferenceCode(
			randomAddress.getExternalReferenceCode());

		randomPatchCart.setBillingAddressId(0L);
		randomPatchCart.setShippingAddressExternalReferenceCode(
			randomAddress.getExternalReferenceCode());
		randomPatchCart.setShippingAddressId(0L);

		Cart patchCart = cartResource.patchCartByExternalReferenceCode(
			postCart.getExternalReferenceCode(), randomPatchCart);

		randomPatchCart.setBillingAddressId(randomAddress.getAddressId());
		randomPatchCart.setShippingAddressId(randomAddress.getAddressId());

		Cart expectedPatchCart = postCart.clone();

		BeanTestUtil.copyProperties(randomPatchCart, expectedPatchCart);

		Cart getCart = cartResource.getCartByExternalReferenceCode(
			patchCart.getExternalReferenceCode());

		assertEquals(expectedPatchCart, getCart);
		assertValid(getCart);
		Assert.assertEquals(
			randomAddress.getAddressId(),
			GetterUtil.getLong(getCart.getBillingAddressId()));
		Assert.assertEquals(
			randomAddress.getExternalReferenceCode(),
			getCart.getBillingAddressExternalReferenceCode());
		Assert.assertEquals(
			randomAddress.getAddressId(),
			GetterUtil.getLong(getCart.getShippingAddressId()));
		Assert.assertEquals(
			randomAddress.getExternalReferenceCode(),
			getCart.getShippingAddressExternalReferenceCode());
	}

	private void _testPatchCartWithMoreExternalReferenceCodes()
		throws Exception {

		Cart postCart = cartResource.postChannelCart(
			_commerceChannel.getCommerceChannelId(), randomCart());

		Cart randomPatchCart = randomPatchCart();

		Address randomAddress = _addressLocalService.addAddress(
			RandomTestUtil.randomString(), _user.getUserId(),
			AccountEntry.class.getName(), _accountEntry.getAccountEntryId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), _region.getRegionId(),
			_country.getCountryId(), 0, false, true,
			RandomTestUtil.randomString(), _serviceContext);

		randomPatchCart.setBillingAddressExternalReferenceCode(
			randomAddress.getExternalReferenceCode());

		randomPatchCart.setBillingAddressId(0L);

		randomPatchCart.setShippingAddressExternalReferenceCode(
			randomAddress.getExternalReferenceCode());
		randomPatchCart.setShippingAddressId(0L);

		Cart patchCart = cartResource.patchCart(
			postCart.getId(), randomPatchCart);

		randomPatchCart.setBillingAddressId(randomAddress.getAddressId());
		randomPatchCart.setShippingAddressId(randomAddress.getAddressId());

		Cart expectedPatchCart = postCart.clone();

		BeanTestUtil.copyProperties(randomPatchCart, expectedPatchCart);

		Cart getCart = cartResource.getCart(patchCart.getId());

		assertEquals(expectedPatchCart, getCart);
		assertValid(getCart);
		Assert.assertEquals(
			randomAddress.getAddressId(),
			GetterUtil.getLong(getCart.getBillingAddressId()));
		Assert.assertEquals(
			randomAddress.getExternalReferenceCode(),
			getCart.getBillingAddressExternalReferenceCode());
		Assert.assertEquals(
			randomAddress.getAddressId(),
			GetterUtil.getLong(getCart.getShippingAddressId()));
		Assert.assertEquals(
			randomAddress.getExternalReferenceCode(),
			getCart.getShippingAddressExternalReferenceCode());
	}

	private void _testPostChannelCartByExternalReferenceCodeWithMoreExternalReferenceCodes()
		throws Exception {

		Cart randomCart = randomCart();

		Address randomAddress = _addressLocalService.addAddress(
			RandomTestUtil.randomString(), _user.getUserId(),
			AccountEntry.class.getName(), _accountEntry.getAccountEntryId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), _region.getRegionId(),
			_country.getCountryId(), 0, false, true,
			RandomTestUtil.randomString(), _serviceContext);

		randomCart.setBillingAddressExternalReferenceCode(
			randomAddress.getExternalReferenceCode());

		randomCart.setBillingAddressId(0L);
		randomCart.setShippingAddressExternalReferenceCode(
			randomAddress.getExternalReferenceCode());
		randomCart.setShippingAddressId(0L);

		Cart postCart = cartResource.postChannelCartByExternalReferenceCode(
			_commerceChannel.getExternalReferenceCode(), randomCart);

		randomCart.setBillingAddressId(randomAddress.getAddressId());
		randomCart.setShippingAddressId(randomAddress.getAddressId());

		assertEquals(randomCart, postCart);
		assertValid(postCart);
		Assert.assertEquals(
			randomAddress.getAddressId(),
			GetterUtil.getLong(postCart.getBillingAddressId()));
		Assert.assertEquals(
			randomAddress.getExternalReferenceCode(),
			postCart.getBillingAddressExternalReferenceCode());
		Assert.assertEquals(
			randomAddress.getAddressId(),
			GetterUtil.getLong(postCart.getShippingAddressId()));
		Assert.assertEquals(
			randomAddress.getExternalReferenceCode(),
			postCart.getShippingAddressExternalReferenceCode());
	}

	private void _testPostChannelCartWithMoreExternalReferenceCodes()
		throws Exception {

		Cart randomCart = randomCart();

		Address randomAddress = _addressLocalService.addAddress(
			RandomTestUtil.randomString(), _user.getUserId(),
			AccountEntry.class.getName(), _accountEntry.getAccountEntryId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), _region.getRegionId(),
			_country.getCountryId(), 0, false, true,
			RandomTestUtil.randomString(), _serviceContext);

		randomCart.setBillingAddressExternalReferenceCode(
			randomAddress.getExternalReferenceCode());

		randomCart.setBillingAddressId(0L);
		randomCart.setShippingAddressExternalReferenceCode(
			randomAddress.getExternalReferenceCode());
		randomCart.setShippingAddressId(0L);

		Cart postCart = testPostChannelCart_addCart(randomCart);

		randomCart.setBillingAddressId(randomAddress.getAddressId());
		randomCart.setShippingAddressId(randomAddress.getAddressId());

		assertEquals(randomCart, postCart);
		assertValid(postCart);
		Assert.assertEquals(
			randomAddress.getAddressId(),
			GetterUtil.getLong(postCart.getBillingAddressId()));
		Assert.assertEquals(
			randomAddress.getExternalReferenceCode(),
			postCart.getBillingAddressExternalReferenceCode());
		Assert.assertEquals(
			randomAddress.getAddressId(),
			GetterUtil.getLong(postCart.getShippingAddressId()));
		Assert.assertEquals(
			randomAddress.getExternalReferenceCode(),
			postCart.getShippingAddressExternalReferenceCode());
	}

	private void _testPutCartByExternalReferenceCodeWithMoreExternalReferenceCodes()
		throws Exception {

		Cart postCart = cartResource.postChannelCart(
			_commerceChannel.getCommerceChannelId(), randomCart());

		Cart randomCart = randomCart();

		Address randomAddress = _addressLocalService.addAddress(
			RandomTestUtil.randomString(), _user.getUserId(),
			AccountEntry.class.getName(), _accountEntry.getAccountEntryId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), _region.getRegionId(),
			_country.getCountryId(), 0, false, true,
			RandomTestUtil.randomString(), _serviceContext);

		randomCart.setBillingAddressExternalReferenceCode(
			randomAddress.getExternalReferenceCode());

		randomCart.setBillingAddressId(0L);

		randomCart.setShippingAddressExternalReferenceCode(
			randomAddress.getExternalReferenceCode());
		randomCart.setShippingAddressId(0L);

		Cart putCart = cartResource.putCartByExternalReferenceCode(
			postCart.getExternalReferenceCode(), randomCart);

		randomCart.setBillingAddressId(randomAddress.getAddressId());
		randomCart.setShippingAddressId(randomAddress.getAddressId());

		assertEquals(randomCart, putCart);
		assertValid(putCart);

		Cart getCart = cartResource.getCartByExternalReferenceCode(
			putCart.getExternalReferenceCode());

		assertEquals(randomCart, getCart);
		assertValid(getCart);
		Assert.assertEquals(
			randomAddress.getAddressId(),
			GetterUtil.getLong(getCart.getBillingAddressId()));
		Assert.assertEquals(
			randomAddress.getExternalReferenceCode(),
			getCart.getBillingAddressExternalReferenceCode());
		Assert.assertEquals(
			randomAddress.getAddressId(),
			GetterUtil.getLong(getCart.getShippingAddressId()));
		Assert.assertEquals(
			randomAddress.getExternalReferenceCode(),
			getCart.getShippingAddressExternalReferenceCode());
	}

	private void _testPutCartWithMoreExternalReferenceCodes() throws Exception {
		Cart postCart = cartResource.postChannelCart(
			_commerceChannel.getCommerceChannelId(), randomCart());

		Cart randomCart = randomCart();

		Address randomAddress = _addressLocalService.addAddress(
			RandomTestUtil.randomString(), _user.getUserId(),
			AccountEntry.class.getName(), _accountEntry.getAccountEntryId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), _region.getRegionId(),
			_country.getCountryId(), 0, false, true,
			RandomTestUtil.randomString(), _serviceContext);

		randomCart.setBillingAddressExternalReferenceCode(
			randomAddress.getExternalReferenceCode());

		randomCart.setBillingAddressId(0L);
		randomCart.setShippingAddressExternalReferenceCode(
			randomAddress.getExternalReferenceCode());
		randomCart.setShippingAddressId(0L);

		Cart putCart = cartResource.putCart(postCart.getId(), randomCart);

		randomCart.setBillingAddressId(randomAddress.getAddressId());
		randomCart.setShippingAddressId(randomAddress.getAddressId());

		assertEquals(randomCart, putCart);
		assertValid(putCart);

		Cart getCart = cartResource.getCart(putCart.getId());

		assertEquals(randomCart, getCart);
		assertValid(getCart);
		Assert.assertEquals(
			randomAddress.getAddressId(),
			GetterUtil.getLong(getCart.getBillingAddressId()));
		Assert.assertEquals(
			randomAddress.getExternalReferenceCode(),
			getCart.getBillingAddressExternalReferenceCode());
		Assert.assertEquals(
			randomAddress.getAddressId(),
			GetterUtil.getLong(getCart.getShippingAddressId()));
		Assert.assertEquals(
			randomAddress.getExternalReferenceCode(),
			getCart.getShippingAddressExternalReferenceCode());
	}

	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AddressLocalService _addressLocalService;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	private CommerceOrder _commerceOrder;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	@DeleteAfterTestRun
	private Country _country;

	@Inject
	private CountryLocalService _countryLocalService;

	@DeleteAfterTestRun
	private Region _region;

	@Inject
	private RegionLocalService _regionLocalService;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}