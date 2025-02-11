/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalServiceUtil;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceShipmentService;
import com.liferay.commerce.shipment.test.util.CommerceShipmentTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;

import org.frutilla.FrutillaRule;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Joao Cordeiro
 */
@RunWith(Arquillian.class)
public class CommerceShipmentServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = CompanyLocalServiceUtil.getCompany(_group.getCompanyId());

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());
		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		_commerceChannel = CommerceChannelLocalServiceUtil.addCommerceChannel(
			null, AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
			_group.getGroupId(), "Test Channel",
			CommerceChannelConstants.CHANNEL_TYPE_SITE, null,
			_commerceCurrency.getCode(), _serviceContext);

		_originalName = PrincipalThreadLocal.getName();
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
		_user = UserTestUtil.addUser(_company);
	}

	@After
	public void tearDown() throws Exception {
		_commerceOrderLocalService.deleteCommerceOrders(
			_commerceChannel.getGroupId());

		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
		PrincipalThreadLocal.setName(_originalName);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddCommerceShipmentByUserWithoutPermission()
		throws Exception {

		frutillaRule.scenario(
			"Adding a new shipment"
		).given(
			"A user"
		).when(
			"The user attempts to add a shipment without permission"
		).then(
			"an exception should be thrown"
		);

		User user = _company.getGuestUser();

		CPInstance cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			_group.getGroupId());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());

		CommerceOrder commerceOrder =
			CommerceTestUtil.createCommerceOrderForShipping(
				_user.getUserId(), _commerceChannel.getGroupId(),
				_commerceCurrency.getCommerceCurrencyId(),
				cpInstance.getCPInstanceId(),
				BigDecimal.valueOf(RandomTestUtil.nextDouble()), BigDecimal.ONE,
				1);

		_commerceShipmentService.addCommerceShipment(
			commerceOrder.getCommerceOrderId(), _serviceContext);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testDeleteCommerceShipmentByUserWithoutPermission()
		throws Exception {

		frutillaRule.scenario(
			"Deleting a shipment"
		).given(
			"A user"
		).when(
			"The user attempts to delete a shipment without permission"
		).then(
			"an exception should be thrown"
		);

		User user = _company.getGuestUser();

		CPInstance cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			_group.getGroupId());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());

		CommerceOrder commerceOrder =
			CommerceTestUtil.createCommerceOrderForShipping(
				_user.getUserId(), _commerceChannel.getGroupId(),
				_commerceCurrency.getCommerceCurrencyId(),
				cpInstance.getCPInstanceId(),
				BigDecimal.valueOf(RandomTestUtil.nextDouble()), BigDecimal.ONE,
				1);

		CommerceShipment commerceShipment =
			CommerceShipmentTestUtil.createEmptyOrderShipment(
				commerceOrder.getGroupId(), commerceOrder.getCommerceOrderId());

		_commerceShipmentService.deleteCommerceShipment(
			commerceShipment.getCommerceShipmentId(), false);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testGetCommerceShipmentByUserWithoutPermission()
		throws Exception {

		frutillaRule.scenario(
			"Fetching a shipment"
		).given(
			"A user"
		).when(
			"The user attempts to fetch a shipment without permission"
		).then(
			"an exception should be thrown"
		);

		User user = _company.getGuestUser();

		CPInstance cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			_group.getGroupId());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());

		CommerceOrder commerceOrder =
			CommerceTestUtil.createCommerceOrderForShipping(
				_user.getUserId(), _commerceChannel.getGroupId(),
				_commerceCurrency.getCommerceCurrencyId(),
				cpInstance.getCPInstanceId(),
				BigDecimal.valueOf(RandomTestUtil.nextDouble()), BigDecimal.ONE,
				1);

		CommerceShipment commerceShipment =
			CommerceShipmentTestUtil.createEmptyOrderShipment(
				commerceOrder.getGroupId(), commerceOrder.getCommerceOrderId());

		_commerceShipmentService.getCommerceShipment(
			commerceShipment.getCommerceShipmentId());
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testUpdateCommerceShipmentByUserWithoutPermission()
		throws Exception {

		frutillaRule.scenario(
			"Updating a shipment"
		).given(
			"A user"
		).when(
			"The user attempts to update a shipment without permission"
		).then(
			"an exception should be thrown"
		);

		User user = _company.getGuestUser();

		CPInstance cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			_group.getGroupId());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());

		CommerceOrder commerceOrder =
			CommerceTestUtil.createCommerceOrderForShipping(
				_user.getUserId(), _commerceChannel.getGroupId(),
				_commerceCurrency.getCommerceCurrencyId(),
				cpInstance.getCPInstanceId(),
				BigDecimal.valueOf(RandomTestUtil.nextDouble()), BigDecimal.ONE,
				1);

		CommerceShipment commerceShipment =
			CommerceShipmentTestUtil.createEmptyOrderShipment(
				commerceOrder.getGroupId(), commerceOrder.getCommerceOrderId());

		commerceShipment.setStatus(3);

		_commerceShipmentService.updateCommerceShipment(commerceShipment);
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private static Company _company;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Inject
	private CommerceShipmentService _commerceShipmentService;

	@DeleteAfterTestRun
	private Group _group;

	private String _originalName;
	private PermissionChecker _originalPermissionChecker;
	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}