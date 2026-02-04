/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.events.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.settings.FallbackKeysSettingsUtil;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import jakarta.servlet.http.Cookie;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Crescenzo Rega
 */
@RunWith(Arquillian.class)
public class LoginPostActionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		Group group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(group.getCompanyId());

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_company.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			group.getGroupId(), _commerceCurrency.getCode());

		Settings settings = FallbackKeysSettingsUtil.getSettings(
			new GroupServiceSettingsLocator(
				_commerceChannel.getGroupId(),
				CommerceConstants.SERVICE_NAME_COMMERCE_ORDER));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		modifiableSettings.setValue(
			"guestCheckoutEnabled", Boolean.TRUE.toString());

		modifiableSettings.store();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setUser(TestPropsValues.getUser());

		_mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		_mockHttpServletRequest.setAttribute(
			WebKeys.USER_ID, TestPropsValues.getUserId());
		_mockHttpServletRequest.setPathInfo(StringPool.BLANK);
		_mockHttpServletRequest.setScheme(Http.HTTPS);
		_mockHttpServletRequest.setSecure(true);
	}

	@Test
	public void testGuestOrderReconciliation() throws Exception {
		User user = _company.getGuestUser();

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, PermissionCheckerFactoryUtil.create(user))) {

			AccountEntry accountEntry =
				_accountEntryLocalService.getGuestAccountEntry(
					_company.getCompanyId());

			CommerceOrder commerceOrder =
				_commerceOrderLocalService.addCommerceOrder(
					user.getUserId(), _commerceChannel.getGroupId(),
					accountEntry.getAccountEntryId(),
					_commerceCurrency.getCode(), 0);

			commerceOrder = CommerceTestUtil.addCheckoutDetailsToCommerceOrder(
				commerceOrder, commerceOrder.getUserId(), false);
			commerceOrder = _commerceOrderEngine.checkoutCommerceOrder(
				commerceOrder, user.getUserId());

			String cookieName =
				CommerceOrder.class.getName() + StringPool.POUND +
					_commerceChannel.getGroupId();

			_mockHttpServletRequest.setCookies(
				new Cookie(
					cookieName,
					commerceOrder.getUuid() + StringPool.PIPE +
						commerceOrder.getOrderDate()));

			_loginPostAction.run(
				_mockHttpServletRequest, new MockHttpServletResponse());

			Cookie[] cookies = ArrayUtil.filter(
				_mockHttpServletRequest.getCookies(),
				cookie -> StringUtil.equalsIgnoreCase(
					cookieName, cookie.getName()));

			Cookie cookie = cookies[0];

			Assert.assertEquals(0, cookie.getMaxAge());
			Assert.assertEquals(StringPool.BLANK, cookie.getValue());
		}
	}

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	private CommerceChannel _commerceChannel;
	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceOrderEngine _commerceOrderEngine;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(
		filter = "component.name=com.liferay.commerce.internal.events.LoginPostAction",
		type = LifecycleAction.class
	)
	private Action _loginPostAction;

	private final MockHttpServletRequest _mockHttpServletRequest =
		new MockHttpServletRequest();

}