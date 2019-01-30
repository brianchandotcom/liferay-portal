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
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.context.AcceptLanguage;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Cristina González
 */
@Component(
	property = JaxrsWhiteboardConstants.JAX_RS_EXTENSION + "=true",
	service = ContextProvider.class
)
@Provider
public class AcceptLanguageContextProvider
	implements ContextProvider<AcceptLanguage> {

	@Override
	public AcceptLanguage createContext(Message message) {
		HttpServletRequest httpServletRequest =
			(HttpServletRequest)message.getContextualProperty("HTTP.REQUEST");

		return new AcceptLanguage() {

			@Override
			public List<Locale> getLocales() {
				String headerAcceptLanguage = httpServletRequest.getHeader(
					HttpHeaders.ACCEPT_LANGUAGE);

				if (headerAcceptLanguage != null) {
					return Collections.list(httpServletRequest.getLocales());
				}

				return Collections.emptyList();
			}

			@Override
			public Locale getPreferredLocale() {
				List<Locale> locales = getLocales();

				if (Objects.nonNull(locales) && !locales.isEmpty()) {
					return locales.get(0);
				}

				try {
					User user = _portal.initUser(httpServletRequest);

					return user.getLocale();
				}
				catch (NoSuchUserException nsue) {
					throw new NotFoundException(
						"Unable to obtain preferred locale because: no user " +
							"could be found",
						nsue);
				}
				catch (Throwable t) {
					throw new InternalServerErrorException(
						"Unable to obtain preferred locale because: " +
							t.getMessage(),
						t);
				}
			}

		};
	}

	@Reference
	private Portal _portal;

}