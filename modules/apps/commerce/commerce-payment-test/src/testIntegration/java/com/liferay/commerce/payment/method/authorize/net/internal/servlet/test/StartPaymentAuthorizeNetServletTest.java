/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.method.authorize.net.internal.servlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.portal.kernel.encryptor.Encryptor;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PortalImpl;

import jakarta.servlet.Servlet;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Brian I. Kim
 */
@RunWith(Arquillian.class)
public class StartPaymentAuthorizeNetServletTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());

		_user = UserTestUtil.addUser();
	}

	@Test
	public void testStartPaymentAuthorizeNetServletWithGuestUser()
		throws Exception {

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user));
			LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				PortalImpl.class.getName(), LoggerTestUtil.OFF)) {

			CommerceCurrency commerceCurrency =
				CommerceCurrencyTestUtil.addCommerceCurrency(
					_group.getCompanyId());

			CommerceChannel commerceChannel =
				CommerceTestUtil.addCommerceChannel(
					_group.getGroupId(), commerceCurrency.getCode());

			CommerceOrder commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
				_user.getUserId(), commerceChannel.getGroupId(),
				commerceCurrency);

			MockHttpServletRequest mockHttpServletRequest =
				new MockHttpServletRequest(
					"GET", "/start-authorizenet-payment");

			mockHttpServletRequest.setParameter(
				"groupId", String.valueOf(commerceOrder.getGroupId()));
			mockHttpServletRequest.setParameter(
				"uuid", String.valueOf(commerceOrder.getUuid()));

			MockHttpServletResponse mockHttpServletResponse =
				new MockHttpServletResponse();

			_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

			Assert.assertEquals(
				HttpServletResponse.SC_BAD_REQUEST,
				mockHttpServletResponse.getStatus());

			mockHttpServletRequest.addParameter(
				"guestToken",
				_encryptor.encrypt(
					_company.getKeyObj(),
					String.valueOf(commerceOrder.getCommerceOrderId())));

			mockHttpServletResponse = new MockHttpServletResponse();

			_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

			String contentString = mockHttpServletResponse.getContentAsString();

			Assert.assertTrue(contentString.contains("formAuthorizeNet"));
		}
	}

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private Encryptor _encryptor;

	private Group _group;

	@Inject(
		filter = "osgi.http.whiteboard.servlet.name=com.liferay.commerce.payment.method.authorize.net.internal.servlet.StartPaymentAuthorizeNetServlet"
	)
	private Servlet _servlet;

	private User _user;

}