/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.validator.vies.web.internal.display.context;

import com.liferay.account.validator.vies.configuration.VIESAccountEntryValidatorConfiguration;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Crescenzo Rega
 */
public class VIESAccountEntryValidatorConfigurationDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_currentCountryA2 = RandomTestUtil.randomString();

		Mockito.when(
			_viesAccountEntryValidatorConfiguration.countries()
		).thenReturn(
			new String[] {_currentCountryA2}
		);

		Mockito.when(
			_configurationProvider.getCompanyConfiguration(
				Mockito.eq(VIESAccountEntryValidatorConfiguration.class),
				Mockito.anyLong())
		).thenReturn(
			_viesAccountEntryValidatorConfiguration
		);

		_availableCountryA2 = RandomTestUtil.randomString();

		Country availableCountry = _mockCountry(
			_availableCountryA2, RandomTestUtil.randomString());

		Country currentCountry = _mockCountry(
			_currentCountryA2, RandomTestUtil.randomString());

		Mockito.when(
			_countryService.getCompanyCountries(
				Mockito.anyLong(), Mockito.eq(true))
		).thenReturn(
			Arrays.asList(availableCountry, currentCountry)
		);

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			themeDisplay.getCompanyId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			themeDisplay.getLocale()
		).thenReturn(
			LocaleUtil.US
		);

		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Mockito.when(
			httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		_viesAccountEntryValidatorConfigurationDisplayContext =
			new VIESAccountEntryValidatorConfigurationDisplayContext(
				_configurationProvider, _countryService, httpServletRequest);
	}

	@Test
	public void testGetAvailableCountries() {
		List<KeyValuePair> availableCountries =
			_viesAccountEntryValidatorConfigurationDisplayContext.
				getAvailableCountries();

		Assert.assertEquals(
			availableCountries.toString(), 1, availableCountries.size());

		KeyValuePair country = availableCountries.get(0);

		Assert.assertEquals(_availableCountryA2, country.getKey());
	}

	@Test
	public void testGetCheckInterval() {
		int checkInterval = RandomTestUtil.randomInt();

		Mockito.when(
			_viesAccountEntryValidatorConfiguration.checkInterval()
		).thenReturn(
			checkInterval
		);

		Assert.assertEquals(
			checkInterval,
			_viesAccountEntryValidatorConfigurationDisplayContext.
				getCheckInterval());
	}

	@Test
	public void testGetCurrentCountries() {
		List<KeyValuePair> currentCountries =
			_viesAccountEntryValidatorConfigurationDisplayContext.
				getCurrentCountries();

		Assert.assertEquals(
			currentCountries.toString(), 1, currentCountries.size());

		KeyValuePair currentCountry = currentCountries.get(0);

		Assert.assertEquals(_currentCountryA2, currentCountry.getKey());
	}

	@Test
	public void testGetVIESEndpointURL() {
		String viesEndpointURL = RandomTestUtil.randomString();

		Mockito.when(
			_viesAccountEntryValidatorConfiguration.viesEndpointURL()
		).thenReturn(
			viesEndpointURL
		);

		Assert.assertEquals(
			viesEndpointURL,
			_viesAccountEntryValidatorConfigurationDisplayContext.
				getVIESEndpointURL());
	}

	@Test
	public void testIsEnabled() {
		Mockito.when(
			_viesAccountEntryValidatorConfiguration.enabled()
		).thenReturn(
			true
		);

		Assert.assertTrue(
			_viesAccountEntryValidatorConfigurationDisplayContext.isEnabled());
	}

	private Country _mockCountry(String a2, String name) {
		Country country = Mockito.mock(Country.class);

		Mockito.when(
			country.getA2()
		).thenReturn(
			a2
		);

		Mockito.when(
			country.getName(LocaleUtil.US)
		).thenReturn(
			name
		);

		return country;
	}

	private String _availableCountryA2;
	private final ConfigurationProvider _configurationProvider = Mockito.mock(
		ConfigurationProvider.class);
	private final CountryService _countryService = Mockito.mock(
		CountryService.class);
	private String _currentCountryA2;
	private final VIESAccountEntryValidatorConfiguration
		_viesAccountEntryValidatorConfiguration = Mockito.mock(
			VIESAccountEntryValidatorConfiguration.class);
	private VIESAccountEntryValidatorConfigurationDisplayContext
		_viesAccountEntryValidatorConfigurationDisplayContext;

}