/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.internal.integration;

import com.liferay.commerce.payment.internal.configuration.FunctionCommercePaymentIntegrationConfiguration;
import com.liferay.commerce.payment.model.CommercePaymentEntry;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.model.impl.CommercePaymentEntryImpl;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelLocalService;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.portal.catapult.PortalCatapult;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.BigDecimalUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Crescenzo Rega
 */
public class FunctionCommercePaymentIntegrationTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		_setUpCommerceChannelLocalService();
		_setUpCommercePaymentMethodGroupRelLocalService();
		_setUpFunctionCommercePaymentIntegrationConfiguration();
		_setUpLanguage();
		_setUpPortal();
		_setUpPortalCatapult();
		_setUpResourceBundleLoader();
	}

	@After
	public void tearDown() {
		_languageUtilMockedStatic.close();
	}

	@Test
	public void testAuthorize() throws Exception {
		double amount = 0.0;

		_setUpJSONFactory(amount);

		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Mockito.when(
			httpServletRequest.getParameterMap()
		).thenReturn(
			HashMapBuilder.put(
				RandomTestUtil.randomString(),
				new String[] {RandomTestUtil.randomString()}
			).build()
		);

		CommercePaymentEntry commercePaymentEntry =
			_functionCommercePaymentIntegration.authorize(
				httpServletRequest, _getCommercePaymentEntry());

		Assert.assertTrue(
			BigDecimalUtil.eq(
				commercePaymentEntry.getAmount(), BigDecimal.valueOf(amount)));
	}

	@Test
	public void testCapture() throws Exception {
		double amount = 1.0;

		_setUpJSONFactory(amount);

		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Mockito.when(
			httpServletRequest.getParameterMap()
		).thenReturn(
			HashMapBuilder.put(
				RandomTestUtil.randomString(),
				new String[] {RandomTestUtil.randomString()}
			).build()
		);

		CommercePaymentEntry commercePaymentEntry =
			_functionCommercePaymentIntegration.capture(
				httpServletRequest, _getCommercePaymentEntry());

		Assert.assertTrue(
			BigDecimalUtil.eq(
				commercePaymentEntry.getAmount(), BigDecimal.valueOf(amount)));
	}

	@Test
	public void testSetUpPayment() throws Exception {
		double amount = 25.60;

		_setUpJSONFactory(amount);

		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Mockito.when(
			httpServletRequest.getParameterMap()
		).thenReturn(
			HashMapBuilder.put(
				RandomTestUtil.randomString(),
				new String[] {RandomTestUtil.randomString()}
			).build()
		);

		CommercePaymentEntry commercePaymentEntry =
			_functionCommercePaymentIntegration.capture(
				httpServletRequest, _getCommercePaymentEntry());

		Assert.assertTrue(
			BigDecimalUtil.eq(
				commercePaymentEntry.getAmount(), BigDecimal.valueOf(amount)));
	}

	private CommercePaymentEntry _getCommercePaymentEntry() {
		CommercePaymentEntry commercePaymentEntry =
			new CommercePaymentEntryImpl();

		commercePaymentEntry.setCompanyId(RandomTestUtil.randomLong());
		commercePaymentEntry.setUserId(RandomTestUtil.randomLong());
		commercePaymentEntry.setClassNameId(RandomTestUtil.randomLong());
		commercePaymentEntry.setCommerceChannelId(RandomTestUtil.randomLong());
		commercePaymentEntry.setLanguageId("en_US");

		return commercePaymentEntry;
	}

	private void _setUpCommerceChannelLocalService() throws Exception {
		CommerceChannel commerceChannel = Mockito.mock(CommerceChannel.class);

		Mockito.when(
			commerceChannel.getGroupId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			_commerceChannelLocalService.getCommerceChannel(Mockito.anyLong())
		).thenReturn(
			commerceChannel
		);
	}

	private void _setUpCommercePaymentMethodGroupRelLocalService() {
		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
			Mockito.mock(CommercePaymentMethodGroupRel.class);

		Mockito.when(
			commercePaymentMethodGroupRel.getTypeSettingsUnicodeProperties()
		).thenReturn(
			RandomTestUtil.randomUnicodeProperties(4, 3, 3)
		);

		Mockito.when(
			_commercePaymentMethodGroupRelLocalService.
				fetchCommercePaymentMethodGroupRel(
					Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			commercePaymentMethodGroupRel
		);
	}

	private void _setUpFunctionCommercePaymentIntegrationConfiguration() {
		Mockito.when(
			_functionCommercePaymentIntegrationConfiguration.key()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_functionCommercePaymentIntegrationConfiguration.name()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_functionCommercePaymentIntegrationConfiguration.
				oAuth2ApplicationExternalReferenceCode()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_functionCommercePaymentIntegrationConfiguration.
				paymentIntegrationType()
		).thenReturn(
			RandomTestUtil.randomInt()
		);

		Mockito.when(
			_functionCommercePaymentIntegrationConfiguration.
				paymentIntegrationTypeSettings()
		).thenReturn(
			RandomTestUtil.randomString()
		);
	}

	private void _setUpJSONFactory(double amount) throws Exception {
		Mockito.when(
			_jsonFactory.createJSONObject(Mockito.anyString())
		).thenReturn(
			JSONUtil.put(
				"amount", amount
			).put(
				"paymentStatus", 2
			).put(
				"transactionCode", RandomTestUtil.randomString()
			)
		);

		Mockito.when(
			_jsonFactory.looseSerializeDeep(Mockito.any())
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_jsonFactory.createJSONObject()
		).thenReturn(
			new JSONObjectImpl()
		);
	}

	private void _setUpLanguage() throws Exception {
		Mockito.when(
			_language.get(
				Mockito.any(ResourceBundle.class), Mockito.anyString())
		).thenReturn(
			RandomTestUtil.randomString()
		);

		_languageUtilMockedStatic.when(
			() -> LanguageUtil.isAvailableLocale(Mockito.any(Locale.class))
		).thenReturn(
			true
		);
	}

	private void _setUpPortal() {
		Mockito.when(
			_portal.getClassName(Mockito.anyLong())
		).thenReturn(
			RandomTestUtil.randomString()
		);
	}

	private void _setUpPortalCatapult() throws Exception {
		Future<byte[]> future = Mockito.mock(Future.class);

		Mockito.when(
			future.get()
		).thenReturn(
			RandomTestUtil.randomBytes()
		);

		Mockito.when(
			_portalCatapult.launch(
				Mockito.anyLong(), Mockito.any(), Mockito.anyString(),
				Mockito.any(), Mockito.anyString(), Mockito.anyLong())
		).thenReturn(
			future
		);
	}

	private void _setUpResourceBundleLoader() throws Exception {
		ResourceBundleLoader resourceBundleLoader = Mockito.mock(
			ResourceBundleLoader.class);

		ResourceBundleLoaderUtil.setPortalResourceBundleLoader(
			resourceBundleLoader);

		Mockito.when(
			resourceBundleLoader.loadResourceBundle(Mockito.any())
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);
	}

	@Mock
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Mock
	private CommercePaymentMethodGroupRelLocalService
		_commercePaymentMethodGroupRelLocalService;

	@InjectMocks
	private FunctionCommercePaymentIntegration
		_functionCommercePaymentIntegration;

	@Mock
	private FunctionCommercePaymentIntegrationConfiguration
		_functionCommercePaymentIntegrationConfiguration;

	@Mock
	private JSONFactory _jsonFactory;

	@Mock
	private Language _language;

	private final MockedStatic<LanguageUtil> _languageUtilMockedStatic =
		Mockito.mockStatic(LanguageUtil.class);

	@Mock
	private Portal _portal;

	@Mock
	private PortalCatapult _portalCatapult;

}