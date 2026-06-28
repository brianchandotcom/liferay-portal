/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.validator.vies.web.internal.display.context;

import com.liferay.account.validator.vies.configuration.VIESAccountEntryValidatorConfiguration;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.KeyValuePairComparator;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Crescenzo Rega
 */
public class VIESAccountEntryValidatorConfigurationDisplayContext {

	public VIESAccountEntryValidatorConfigurationDisplayContext(
		ConfigurationProvider configurationProvider,
		CountryService countryService, HttpServletRequest httpServletRequest) {

		_configurationProvider = configurationProvider;
		_countryService = countryService;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<KeyValuePair> getAvailableCountries() {
		VIESAccountEntryValidatorConfiguration
			viesAccountEntryValidatorConfiguration =
				_getVIESAccountEntryValidatorConfiguration();

		Set<String> countryCodes = SetUtil.fromArray(
			viesAccountEntryValidatorConfiguration.countryCodes());

		return ListUtil.sort(
			TransformUtil.transform(
				_getCompanyCountries(),
				country -> {
					if (countryCodes.contains(country.getA2())) {
						return null;
					}

					return new KeyValuePair(
						country.getA2(),
						country.getName(_themeDisplay.getLocale()));
				}),
			_keyValuePairComparator);
	}

	public int getCheckInterval() {
		VIESAccountEntryValidatorConfiguration
			viesAccountEntryValidatorConfiguration =
				_getVIESAccountEntryValidatorConfiguration();

		return viesAccountEntryValidatorConfiguration.checkInterval();
	}

	public List<KeyValuePair> getCurrentCountries() {
		VIESAccountEntryValidatorConfiguration
			viesAccountEntryValidatorConfiguration =
				_getVIESAccountEntryValidatorConfiguration();

		Set<String> countryCodes = SetUtil.fromArray(
			viesAccountEntryValidatorConfiguration.countryCodes());

		return ListUtil.sort(
			TransformUtil.transform(
				_getCompanyCountries(),
				country -> {
					if (!countryCodes.contains(country.getA2())) {
						return null;
					}

					return new KeyValuePair(
						country.getA2(),
						country.getName(_themeDisplay.getLocale()));
				}),
			_keyValuePairComparator);
	}

	public String getVIESEndpointURL() {
		VIESAccountEntryValidatorConfiguration
			viesAccountEntryValidatorConfiguration =
				_getVIESAccountEntryValidatorConfiguration();

		return viesAccountEntryValidatorConfiguration.viesEndpointURL();
	}

	public boolean isEnabled() {
		VIESAccountEntryValidatorConfiguration
			viesAccountEntryValidatorConfiguration =
				_getVIESAccountEntryValidatorConfiguration();

		return viesAccountEntryValidatorConfiguration.enabled();
	}

	private List<Country> _getCompanyCountries() {
		if (_companyCountries != null) {
			return _companyCountries;
		}

		try {
			_companyCountries = _countryService.getCompanyCountries(
				_themeDisplay.getCompanyId(), true);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			_companyCountries = new ArrayList<>();
		}

		return _companyCountries;
	}

	private VIESAccountEntryValidatorConfiguration
		_getVIESAccountEntryValidatorConfiguration() {

		if (_viesAccountEntryValidatorConfiguration != null) {
			return _viesAccountEntryValidatorConfiguration;
		}

		try {
			_viesAccountEntryValidatorConfiguration =
				_configurationProvider.getCompanyConfiguration(
					VIESAccountEntryValidatorConfiguration.class,
					_themeDisplay.getCompanyId());
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		return _viesAccountEntryValidatorConfiguration;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VIESAccountEntryValidatorConfigurationDisplayContext.class);

	private List<Country> _companyCountries;
	private final ConfigurationProvider _configurationProvider;
	private final CountryService _countryService;
	private final KeyValuePairComparator _keyValuePairComparator =
		new KeyValuePairComparator(false, true);
	private final ThemeDisplay _themeDisplay;
	private VIESAccountEntryValidatorConfiguration
		_viesAccountEntryValidatorConfiguration;

}