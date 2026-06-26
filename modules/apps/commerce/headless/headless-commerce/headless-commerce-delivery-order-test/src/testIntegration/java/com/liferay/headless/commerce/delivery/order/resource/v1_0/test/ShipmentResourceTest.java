/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.order.resource.v1_0.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.product.type.virtual.constants.VirtualCPTypeConstants;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceShipmentItemLocalService;
import com.liferay.commerce.service.CommerceShipmentLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.headless.commerce.delivery.order.client.dto.v1_0.Shipment;
import com.liferay.headless.commerce.delivery.order.client.pagination.Page;
import com.liferay.headless.commerce.delivery.order.client.pagination.Pagination;
import com.liferay.headless.commerce.delivery.order.client.resource.v1_0.ShipmentResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.settings.FallbackKeysSettingsUtil;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.test.rule.Inject;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Andrea Sbarra
 * @author Alessio Antonio Rendina
 */
@RunWith(Arquillian.class)
public class ShipmentResourceTest extends BaseShipmentResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		_userLocalService.updatePassword(
			_user.getUserId(), _PASSWORD, _PASSWORD, false, true);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		PrincipalThreadLocal.setName(_user.getUserId());

		_accountEntry = _addBusinessAccountEntry();

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			_accountEntry.getAccountEntryId(), _user.getUserId());

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			testCompany.getCompanyId());

		_commerceCatalog = CommerceTestUtil.addCommerceCatalog(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId(), _commerceCurrency.getCode());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			testGroup.getGroupId(), _commerceCurrency.getCode());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());

		CPDefinition cpDefinition = CPTestUtil.addCPDefinitionFromCatalog(
			_commerceCatalog.getGroupId(), VirtualCPTypeConstants.NAME, true,
			true);

		CommerceTestUtil.updateBackOrderCPDefinitionInventory(cpDefinition);

		List<CPInstance> cpInstances = cpDefinition.getCPInstances();

		_cpInstance = cpInstances.get(0);

		_commerceOrder = _addCommerceOrder();
	}

	@Override
	@Test
	public void testGetPlacedOrderShipmentsPage() throws Exception {
		super.testGetPlacedOrderShipmentsPage();

		_testGetPlacedOrderShipmentsPageWithAccountBuyerAndAccountScope();
		_testGetPlacedOrderShipmentsPageWithAccountBuyerAndUserScope();
		_testGetPlacedOrderShipmentsPageWithAccountOrderManager();
		_testGetPlacedOrderShipmentsPageWithAccountSupplier();
		_testGetPlacedOrderShipmentsPageWithOwner();
		_testGetPlacedOrderShipmentsPageWithUserFromAnotherAccount();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"accountId", "shippingAddressId", "shippingMethodId",
			"shippingOptionName"
		};
	}

	@Override
	protected Collection<EntityField> getEntityFields() throws Exception {
		return new ArrayList<>();
	}

	@Override
	protected Shipment randomShipment() throws Exception {
		return new Shipment() {
			{
				accountId = _commerceOrder.getCommerceAccountId();
				carrier = StringUtil.toLowerCase(RandomTestUtil.randomString());
				createDate = RandomTestUtil.nextDate();
				expectedDate = RandomTestUtil.nextDate();
				externalReferenceCode = RandomTestUtil.randomString();
				modifiedDate = RandomTestUtil.nextDate();
				orderId = _commerceOrder.getCommerceOrderId();
				shippingAddressId = _commerceOrder.getShippingAddressId();
				shippingDate = RandomTestUtil.nextDate();
				shippingMethodId = _commerceOrder.getCommerceShippingMethodId();
				shippingOptionName = _commerceOrder.getShippingOptionName();
				trackingNumber = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				userName = _commerceOrder.getUserName();
			}
		};
	}

	@Override
	protected Shipment
			testGetPlacedOrderByExternalReferenceCodeShipmentsPage_addShipment(
				String externalReferenceCode, Shipment shipment)
		throws Exception {

		_commerceOrderLocalService.updateCommerceOrderExternalReferenceCode(
			externalReferenceCode, _commerceOrder.getCommerceOrderId());

		CommerceShipment commerceShipment = _addCommerceShipment(
			_commerceOrder);

		_addCommerceShipmentItem(
			commerceShipment, _getCommerceOrderItem(_commerceOrder));

		return _toShipment(commerceShipment);
	}

	@Override
	protected String
			testGetPlacedOrderByExternalReferenceCodeShipmentsPage_getExternalReferenceCode()
		throws Exception {

		return _commerceOrder.getExternalReferenceCode();
	}

	@Override
	protected Shipment testGetPlacedOrderShipmentsPage_addShipment(
			Long placedOrderId, Shipment shipment)
		throws Exception {

		CommerceShipment commerceShipment = _addCommerceShipment(
			_commerceOrder);

		_addCommerceShipmentItem(
			commerceShipment, _getCommerceOrderItem(_commerceOrder));

		return _toShipment(commerceShipment);
	}

	@Override
	protected Long testGetPlacedOrderShipmentsPage_getPlacedOrderId()
		throws Exception {

		return _commerceOrder.getCommerceOrderId();
	}

	private User _addAccountUser(String accountRoleName) throws Exception {
		User user = _addUser();

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			_accountEntry.getAccountEntryId(), user.getUserId());

		Role role = _roleLocalService.getRole(
			testCompany.getCompanyId(), accountRoleName);

		_userGroupRoleLocalService.addUserGroupRole(
			user.getUserId(), _accountEntry.getAccountEntryGroupId(),
			role.getRoleId());

		return user;
	}

	private AccountEntry _addBusinessAccountEntry() throws Exception {
		AccountEntry accountEntry = _accountEntryLocalService.addAccountEntry(
			StringPool.BLANK, _user.getUserId(),
			AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(), null, null,
			RandomTestUtil.randomString() + "@liferay.com", null,
			StringPool.BLANK, AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED, _serviceContext);

		_accountEntries.add(accountEntry);

		return accountEntry;
	}

	private CommerceOrder _addCommerceOrder() throws Exception {
		CommerceOrder commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			testGroup.getGroupId(), _user.getUserId(),
			_accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId());

		CommerceTestUtil.addCommerceOrderItem(
			commerceOrder.getCommerceOrderId(), _cpInstance.getCPInstanceId(),
			BigDecimal.TEN);

		commerceOrder = _commerceOrderLocalService.getCommerceOrder(
			commerceOrder.getCommerceOrderId());

		if (_commerceOrder == null) {
			commerceOrder = CommerceTestUtil.addCommerceOrderShippingDetails(
				commerceOrder, BigDecimal.valueOf(RandomTestUtil.nextDouble()));
		}
		else {
			commerceOrder.setBillingAddressId(
				_commerceOrder.getBillingAddressId());
			commerceOrder.setCommerceShippingMethodId(
				_commerceOrder.getCommerceShippingMethodId());
			commerceOrder.setShippingAddressId(
				_commerceOrder.getShippingAddressId());
			commerceOrder.setShippingAmount(_commerceOrder.getShippingAmount());
			commerceOrder.setShippingOptionName(
				_commerceOrder.getShippingOptionName());
		}

		commerceOrder.setOrderStatus(
			CommerceOrderConstants.ORDER_STATUS_PROCESSING);

		commerceOrder = _commerceOrderLocalService.updateCommerceOrder(
			commerceOrder);

		_commerceOrders.add(commerceOrder);

		return commerceOrder;
	}

	private CommerceShipment _addCommerceShipment(CommerceOrder commerceOrder)
		throws Exception {

		CommerceShipment commerceShipment =
			_commerceShipmentLocalService.addCommerceShipment(
				RandomTestUtil.randomString(), commerceOrder.getGroupId(),
				commerceOrder.getCommerceAccountId(),
				commerceOrder.getShippingAddressId(),
				commerceOrder.getCommerceShippingMethodId(),
				commerceOrder.getShippingOptionName(), _serviceContext);

		_commerceShipments.add(commerceShipment);

		return commerceShipment;
	}

	private CommerceShipmentItem _addCommerceShipmentItem(
			CommerceShipment commerceShipment,
			CommerceOrderItem commerceOrderItem)
		throws Exception {

		return _commerceShipmentItemLocalService.addCommerceShipmentItem(
			RandomTestUtil.randomString(),
			commerceShipment.getCommerceShipmentId(),
			commerceOrderItem.getCommerceOrderItemId(), 0, BigDecimal.ONE, null,
			false, _serviceContext);
	}

	private AccountEntry _addSupplierAccountEntry() throws Exception {
		AccountEntry accountEntry = _accountEntryLocalService.addAccountEntry(
			StringPool.BLANK, _user.getUserId(),
			AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(), null, null,
			RandomTestUtil.randomString() + "@liferay.com", null,
			StringPool.BLANK, AccountConstants.ACCOUNT_ENTRY_TYPE_SUPPLIER,
			WorkflowConstants.STATUS_APPROVED, _serviceContext);

		_accountEntries.add(accountEntry);

		return accountEntry;
	}

	private User _addUser() throws Exception {
		User user = UserTestUtil.addUser(testCompany);

		_userLocalService.updatePassword(
			user.getUserId(), _PASSWORD, _PASSWORD, false, true);

		_users.add(user);

		return user;
	}

	private void _assertContainsShipment(User user, long placedOrderId)
		throws Exception {

		ShipmentResource shipmentResource = _getShipmentResource(user);

		Page<Shipment> page = shipmentResource.getPlacedOrderShipmentsPage(
			placedOrderId, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(1, page.getTotalCount());
	}

	private void _assertNotFound(User user, long placedOrderId)
		throws Exception {

		ShipmentResource shipmentResource = _getShipmentResource(user);

		assertHttpResponseStatusCode(
			404,
			shipmentResource.getPlacedOrderShipmentsPageHttpResponse(
				placedOrderId, null, null, Pagination.of(1, 10), null));
	}

	private CommerceOrderItem _getCommerceOrderItem(CommerceOrder commerceOrder)
		throws Exception {

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		return commerceOrderItems.get(0);
	}

	private ShipmentResource _getShipmentResource(User user) {
		return ShipmentResource.builder(
		).authentication(
			user.getEmailAddress(), _PASSWORD
		).endpoint(
			testCompany.getVirtualHostname(),
			PortalUtil.getPortalServerPort(false), "http"
		).locale(
			LocaleUtil.getDefault()
		).build();
	}

	private void _setPlacedOrdersVisibilityScope(String scope)
		throws Exception {

		Settings settings = FallbackKeysSettingsUtil.getSettings(
			new GroupServiceSettingsLocator(
				_commerceOrder.getGroupId(),
				CommerceConstants.SERVICE_NAME_COMMERCE_ORDER));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		modifiableSettings.setValue("placedOrdersVisibilityScope", scope);

		modifiableSettings.store();
	}

	private void _testGetPlacedOrderShipmentsPageWithAccountBuyerAndAccountScope()
		throws Exception {

		CommerceOrder commerceOrder = _addCommerceOrder();

		CommerceShipment commerceShipment = _addCommerceShipment(commerceOrder);

		_addCommerceShipmentItem(
			commerceShipment, _getCommerceOrderItem(commerceOrder));

		_setPlacedOrdersVisibilityScope(
			CommerceOrderConstants.ORDER_VISIBILITY_SCOPE_ACCOUNT);

		User user = _addAccountUser(
			AccountRoleConstants.ROLE_NAME_ACCOUNT_BUYER);

		_assertContainsShipment(user, commerceOrder.getCommerceOrderId());
	}

	private void _testGetPlacedOrderShipmentsPageWithAccountBuyerAndUserScope()
		throws Exception {

		CommerceOrder commerceOrder = _addCommerceOrder();

		CommerceShipment commerceShipment = _addCommerceShipment(commerceOrder);

		_addCommerceShipmentItem(
			commerceShipment, _getCommerceOrderItem(commerceOrder));

		_setPlacedOrdersVisibilityScope(
			CommerceOrderConstants.ORDER_VISIBILITY_SCOPE_USER);

		User user = _addAccountUser(
			AccountRoleConstants.ROLE_NAME_ACCOUNT_BUYER);

		_assertNotFound(user, commerceOrder.getCommerceOrderId());
	}

	private void _testGetPlacedOrderShipmentsPageWithAccountOrderManager()
		throws Exception {

		CommerceOrder commerceOrder = _addCommerceOrder();

		CommerceShipment commerceShipment = _addCommerceShipment(commerceOrder);

		_addCommerceShipmentItem(
			commerceShipment, _getCommerceOrderItem(commerceOrder));

		User user = _addAccountUser(
			AccountRoleConstants.ROLE_NAME_ACCOUNT_ORDER_MANAGER);

		_assertContainsShipment(user, commerceOrder.getCommerceOrderId());
	}

	private void _testGetPlacedOrderShipmentsPageWithAccountSupplier()
		throws Exception {

		CommerceOrder commerceOrder = _addCommerceOrder();

		CommerceShipment commerceShipment = _addCommerceShipment(commerceOrder);

		_addCommerceShipmentItem(
			commerceShipment, _getCommerceOrderItem(commerceOrder));

		AccountEntry supplierAccountEntry = _addSupplierAccountEntry();

		_commerceChannel.setAccountEntryId(
			supplierAccountEntry.getAccountEntryId());

		_commerceChannel = _commerceChannelLocalService.updateCommerceChannel(
			_commerceChannel);

		User user = _addUser();

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			supplierAccountEntry.getAccountEntryId(), user.getUserId());

		Role role = _roleLocalService.getRole(
			testCompany.getCompanyId(),
			AccountRoleConstants.ROLE_NAME_ACCOUNT_SUPPLIER);

		_userGroupRoleLocalService.addUserGroupRole(
			user.getUserId(), supplierAccountEntry.getAccountEntryGroupId(),
			role.getRoleId());

		_assertContainsShipment(user, commerceOrder.getCommerceOrderId());
	}

	private void _testGetPlacedOrderShipmentsPageWithOwner() throws Exception {
		CommerceOrder commerceOrder = _addCommerceOrder();

		CommerceShipment commerceShipment = _addCommerceShipment(commerceOrder);

		_addCommerceShipmentItem(
			commerceShipment, _getCommerceOrderItem(commerceOrder));

		_assertContainsShipment(_user, commerceOrder.getCommerceOrderId());
	}

	private void _testGetPlacedOrderShipmentsPageWithUserFromAnotherAccount()
		throws Exception {

		CommerceOrder commerceOrder = _addCommerceOrder();

		CommerceShipment commerceShipment = _addCommerceShipment(commerceOrder);

		_addCommerceShipmentItem(
			commerceShipment, _getCommerceOrderItem(commerceOrder));

		User user = _addUser();

		AccountEntry otherAccountEntry = _addBusinessAccountEntry();

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			otherAccountEntry.getAccountEntryId(), user.getUserId());

		Role role = _roleLocalService.getRole(
			testCompany.getCompanyId(),
			AccountRoleConstants.ROLE_NAME_ACCOUNT_BUYER);

		_userGroupRoleLocalService.addUserGroupRole(
			user.getUserId(), otherAccountEntry.getAccountEntryGroupId(),
			role.getRoleId());

		_assertNotFound(user, commerceOrder.getCommerceOrderId());
	}

	private Shipment _toShipment(CommerceShipment commerceShipment)
		throws Exception {

		return new Shipment() {
			{
				accountId = commerceShipment.getCommerceAccountId();
				carrier = commerceShipment.getCarrier();
				createDate = commerceShipment.getCreateDate();
				expectedDate = commerceShipment.getExpectedDate();
				externalReferenceCode =
					commerceShipment.getExternalReferenceCode();
				id = commerceShipment.getCommerceShipmentId();
				modifiedDate = commerceShipment.getModifiedDate();
				shippingAddressId = commerceShipment.getCommerceAddressId();
				shippingDate = commerceShipment.getShippingDate();
				shippingMethodId =
					commerceShipment.getCommerceShippingMethodId();
				shippingOptionName = commerceShipment.getShippingOptionName();
				trackingNumber = commerceShipment.getTrackingNumber();
				userName = commerceShipment.getUserName();
			}
		};
	}

	private static final String _PASSWORD = "test";

	@DeleteAfterTestRun
	private final List<AccountEntry> _accountEntries = new ArrayList<>();

	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@DeleteAfterTestRun
	private CommerceCatalog _commerceCatalog;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@Inject
	private CommerceChannelLocalService _commerceChannelLocalService;

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	private CommerceOrder _commerceOrder;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	@DeleteAfterTestRun
	private final List<CommerceOrder> _commerceOrders = new ArrayList<>();

	@Inject
	private CommerceShipmentItemLocalService _commerceShipmentItemLocalService;

	@Inject
	private CommerceShipmentLocalService _commerceShipmentLocalService;

	@DeleteAfterTestRun
	private final List<CommerceShipment> _commerceShipments = new ArrayList<>();

	private CPInstance _cpInstance;

	@Inject
	private RoleLocalService _roleLocalService;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Inject
	private UserLocalService _userLocalService;

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}