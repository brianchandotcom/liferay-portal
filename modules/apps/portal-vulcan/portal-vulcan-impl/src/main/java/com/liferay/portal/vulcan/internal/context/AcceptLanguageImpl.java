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

package com.liferay.portal.vulcan.internal.context;

import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.vulcan.context.AcceptLanguage;

import io.vavr.CheckedFunction0;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.HttpHeaders;

/**
 * @author Cristina González
 */
public class AcceptLanguageImpl implements AcceptLanguage {

	public AcceptLanguageImpl(
		HttpServletRequest httpServletRequest,
		CheckedFunction0<User> userCheckedFunction0) {

		_httpServletRequest = httpServletRequest;
		_userCheckedFunction0 = userCheckedFunction0;
	}

	@Override
	public List<Locale> getLocales() {
		String acceptLanguage = _httpServletRequest.getHeader(
			HttpHeaders.ACCEPT_LANGUAGE);

		if (acceptLanguage == null) {
			return Collections.emptyList();
		}

		return Collections.list(_httpServletRequest.getLocales());
	}

	@Override
	public Locale getPreferredLocale() {
		List<Locale> locales = getLocales();

		if (ListUtil.isNotEmpty(locales)) {
			return locales.get(0);
		}

		try {
			User user = _userCheckedFunction0.apply();

			return user.getLocale();
		}
		catch (NoSuchUserException nsue) {
			throw new NotFoundException(
				"Unable to get preferred locale from nonexistent user", nsue);
		}
		catch (Throwable t) {
			throw new InternalServerErrorException(
				"Unable to get preferred locale: " + t.getMessage(), t);
		}
	}

	private final HttpServletRequest _httpServletRequest;
	private final CheckedFunction0<User> _userCheckedFunction0;

}