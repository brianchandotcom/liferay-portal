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

package com.liferay.portal.vulcan.internal.context.provider;

import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.vulcan.context.AcceptLanguage;
import com.liferay.portal.vulcan.internal.context.provider.base.BaseContextProvider;

import io.vavr.CheckedFunction1;

import java.util.Collections;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

/**
 * Allows JAX-RS resources to provide {@link AcceptLanguage} objects in method
 * parameters, fields or setters by annotating them with {@code
 * javax.ws.rs.core.Context}.
 *
 * @author Alejandro Hernández
 * @author Cristina González
 * @review
 */
@Provider
public class AcceptLanguageContextProvider
	extends BaseContextProvider<AcceptLanguage> {

	public AcceptLanguageContextProvider(
		CheckedFunction1<HttpServletRequest, User> userCheckedFunction1) {

		_userCheckedFunction1 = userCheckedFunction1;
	}

	@Override
	public AcceptLanguage createContext(HttpServletRequest request) {
		return Optional.ofNullable(
			request.getHeader(HttpHeaders.ACCEPT_LANGUAGE)
		).map(
			__ -> Collections.list(request.getLocales())
		).filter(
			ListUtil::isNotEmpty
		).map(
			locales -> (AcceptLanguage)() -> locales
		).orElseGet(
			() -> _getUserAcceptLanguage(request)
		);
	}

	private AcceptLanguage _getUserAcceptLanguage(HttpServletRequest request) {
		try {
			User user = _userCheckedFunction1.apply(request);

			return () -> Collections.singletonList(user.getLocale());
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

	private final CheckedFunction1<HttpServletRequest, User>
		_userCheckedFunction1;

}