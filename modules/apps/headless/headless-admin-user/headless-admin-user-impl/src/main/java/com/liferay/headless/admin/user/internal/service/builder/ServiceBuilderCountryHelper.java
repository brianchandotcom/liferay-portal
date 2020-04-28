/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.admin.user.internal.service.builder;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.service.CountryService;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(immediate = true, service = ServiceBuilderCountryHelper.class)
public class ServiceBuilderCountryHelper {

	public Country toServiceBuilderCountry(String addressCountry) {
		try {
			Country country = _countryService.fetchCountryByA2(addressCountry);

			if (country != null) {
				return country;
			}

			country = _countryService.fetchCountryByA3(addressCountry);

			if (country != null) {
				return country;
			}

			return _countryService.getCountryByName(addressCountry);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}
		}

		return null;
	}

	public long toServiceBuilderCountryId(String addressCountry) {
		return Optional.ofNullable(
			addressCountry
		).map(
			this::toServiceBuilderCountry
		).map(
			Country::getCountryId
		).orElse(
			(long)0
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServiceBuilderCountryHelper.class);

	@Reference
	private CountryService _countryService;

}