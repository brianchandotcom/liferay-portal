/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.dto.v1_0.util;

import com.liferay.headless.admin.user.dto.v1_0.PostalAddress;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.AddressWrapper;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.CountryWrapper;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeWrapper;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Locale;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Balazs Breier
 */
public class PostalAddressUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@AfterClass
	public static void tearDownClass() {
		_languageUtilMockedStatic.close();
	}

	@Before
	public void setUp() throws Exception {
		_languageUtilMockedStatic.reset();

		_address = new AddressWrapper(null) {

			@Override
			public Country getCountry() {
				return new CountryWrapper(null) {

					@Override
					public Map<String, String> getLanguageIdToTitleMap() {
						return HashMapBuilder.put(
							"en_US", "United States"
						).put(
							"pt_BR", "Brasil"
						).build();
					}

					@Override
					public String getTitle(Locale locale) {
						return StringPool.BLANK;
					}

				};
			}

			@Override
			public ListType getListType() {
				return new ListTypeWrapper(null) {

					@Override
					public String getName() {
						return StringPool.BLANK;
					}

				};
			}

		};

		Mockito.when(
			LanguageUtil.getCompanyAvailableLocales(Mockito.anyLong())
		).thenAnswer(
			invocation -> SetUtil.fromArray(LocaleUtil.US, LocaleUtil.BRAZIL)
		);

		Mockito.when(
			LanguageUtil.getLanguageId(Mockito.any(Locale.class))
		).thenAnswer(
			invocation -> {
				Locale locale = invocation.getArgument(0, Locale.class);

				if (locale.equals(LocaleUtil.US)) {
					return "en_US";
				}
				else if (locale.equals(LocaleUtil.BRAZIL)) {
					return "pt_BR";
				}

				return null;
			}
		);
	}

	@Test
	public void testGetAddressCountry_i18n() {
		PostalAddress postalAddress = PostalAddressUtil.toPostalAddress(
			true, _address, 0, LocaleUtil.getDefault());

		Map<String, String> titleMap = postalAddress.getAddressCountry_i18n();

		Assert.assertEquals(titleMap.toString(), 2, titleMap.size());
		Assert.assertTrue(titleMap.containsKey("en_US"));
		Assert.assertTrue(titleMap.containsKey("pt_BR"));
	}

	private static final MockedStatic<LanguageUtil> _languageUtilMockedStatic =
		Mockito.mockStatic(LanguageUtil.class);

	private Address _address;

}