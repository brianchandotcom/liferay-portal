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

package com.liferay.portal.apio.internal.architect.provider;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.language.AcceptLanguage;
import com.liferay.apio.architect.provider.Provider;
import com.liferay.portal.apio.user.CurrentUser;
import com.liferay.portal.kernel.util.Portal;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.HttpHeaders;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Lets resources provide the requested {@link AcceptLanguage} as a parameter in
 * the methods of any of the routes builders.
 *
 * <p>
 * This provider is mandatory in order to use {@code
 * Representor.Builder#addLocalizedStringByLocale(String, BiFunction)} and
 * {@code Representor.Builder#addLocalizedStringByLanguage(String, BiFunction)}
 * methods when declaring a {@link
 * com.liferay.apio.architect.representor.Representor}.
 * </p>
 *
 * @author Rubén Pulido
 * @review
 */
@Component(property = "service.ranking:Integer=101", service = Provider.class)
public class AcceptLanguageProvider implements Provider<AcceptLanguage> {

	@Override
	public AcceptLanguage createContext(HttpServletRequest httpServletRequest) {
		return new AcceptLanguage() {

			/**
			 * Returns the {@code Stream} of the request's preferred
			 * {@code Locale}, in decreasing order.
			 *
			 * <p>
			 * The stream values start with the locale with the highest quality
			 *  value. If there are several locales with the same quality value,
			 *  the locales are returned in the order they were added in the
			 *  {@code Accept-Language} header.
			 * </p>
			 *
			 * <p>
			 * If the request doesn't have an {@code Accept-Language} header,
			 * this method returns an empty {@code Stream}.
			 * </p>
			 *
			 * @return the {@code Stream} of the request's preferred
			 * 		   {@code Locale}, if the {@code Accept-Language} header is
			 * 		   present; otherwise returns an empty {@code Stream}
			 * @review
			 */
			@Override
			public Stream<Locale> getLocales() {
				String headerAcceptLanguage = httpServletRequest.getHeader(
					HttpHeaders.ACCEPT_LANGUAGE);

				if (headerAcceptLanguage != null) {
					Enumeration<Locale> locales =
						httpServletRequest.getLocales();

					List<Locale> localesList = Collections.list(locales);

					return localesList.stream();
				}

				return Stream.empty();
			}

			/**
			 * Returns the {@code Locale} with the highest quality value on the
			 * {@code Accept-Language} header, if that header is present.
			 *
			 * If there are several {@code Locale}s with the same quality value
			 * the first one in the header is returned.
			 *
			 * If the request doesn't have an {@code Accept-Language} header,
			 * this method returns the user's default locale.
			 *
			 * If the request doesn't have an user ,
			 * this method returns the default user's default locale.
			 *
			 * @return the request's {@code Locale} with the highest quality
			 * 			value, if the {@code Accept-Language} header is present;
			 * 			otherwise returns the {@code Enumeration} containing the
			 * 			default locale for the user, if the user is not
			 * 			configured, the locale for the default user.
			 * @review
			 */
			@Override
			public Locale getPreferredLocale() {
				Stream<Locale> localeStream = getLocales();

				Optional<Locale> localeOptional = localeStream.findFirst();

				return localeOptional.orElseGet(
					() -> Try.fromFallible(
						() -> Optional.ofNullable(
							_portal.initUser(httpServletRequest))
					).filter(
						Optional::isPresent
					).map(
						Optional::get
					).map(
						CurrentUser::new
					).map(
						CurrentUser::getLocale
					).orElse(
						null
					)
				);
			}

		};
	}

	@Reference
	private Portal _portal;

}