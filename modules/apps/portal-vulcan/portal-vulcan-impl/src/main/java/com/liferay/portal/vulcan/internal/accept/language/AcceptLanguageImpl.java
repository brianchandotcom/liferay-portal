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

package com.liferay.portal.vulcan.internal.accept.language;

import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.HttpHeaders;

/**
 * @author Cristina González
 */
public class AcceptLanguageImpl implements AcceptLanguage {

	public AcceptLanguageImpl(
		HttpServletRequest httpServletRequest, Language language,
		Portal portal) {

		_httpServletRequest = httpServletRequest;
		_language = language;
		_portal = portal;
	}

	@Override
	public List<Locale> getLocales() {
		String acceptLanguage = _httpServletRequest.getHeader(
			HttpHeaders.ACCEPT_LANGUAGE);

		if (acceptLanguage == null) {
			return Collections.emptyList();
		}

		if (acceptLanguage.equals("zh-Hans-CN")) {
			acceptLanguage = "zh-CN";
		}
		else if (acceptLanguage.equals("zh-Hant-TW")) {
			acceptLanguage = "zh-TW";
		}

		try {
			Company company = _portal.getCompany(_httpServletRequest);

			Set<Locale> companyAvailableLocales =
				_language.getCompanyAvailableLocales(company.getCompanyId());

			List<Locale> locales = Locale.filter(
				Locale.LanguageRange.parse(acceptLanguage),
				companyAvailableLocales);

			if (ListUtil.isEmpty(locales)) {
				throw new ClientErrorException(
					"No available locale matches the accepted languages: " +
						acceptLanguage,
					422);
			}

			return locales;
		}
		catch (PortalException pe) {
			throw new InternalServerErrorException(
				"Unable to get preferred locale: " + pe.getMessage(), pe);
		}
	}

	@Override
	public String getPreferredLanguageId() {
		return LocaleUtil.toLanguageId(getPreferredLocale());
	}

	@Override
	public Locale getPreferredLocale() {
		List<Locale> locales = getLocales();

		if (ListUtil.isNotEmpty(locales)) {
			return locales.get(0);
		}

		try {
			User user = _portal.initUser(_httpServletRequest);

			return user.getLocale();
		}
		catch (NoSuchUserException nsue) {
			throw new NotFoundException(
				"Unable to get preferred locale from nonexistent user", nsue);
		}
		catch (Exception e) {
			throw new InternalServerErrorException(
				"Unable to get preferred locale: " + e.getMessage(), e);
		}
	}

	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final Portal _portal;

}