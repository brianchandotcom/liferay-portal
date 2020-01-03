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

package com.liferay.portal.vulcan.internal.jaxrs.container.request.filter;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.internal.accept.language.AcceptLanguageImpl;
import com.liferay.portal.vulcan.internal.jaxrs.context.provider.ContextProviderUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxrs.impl.UriInfoImpl;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;

/**
 * @author Javier Gamarra
 */
@Provider
public class ContextContainerRequestFilter implements ContainerRequestFilter {

	public ContextContainerRequestFilter(Language language, Portal portal) {
		_language = language;
		_portal = portal;
	}

	@Override
	public void filter(ContainerRequestContext containerRequestContext) {
		handleMessage(PhaseInterceptorChain.getCurrentMessage());
	}

	public void handleMessage(Message message) throws Fault {
		try {
			_handleMessage(message);
		}
		catch (Exception e) {
			throw new Fault(e);
		}
	}

	private void _handleMessage(Message message) throws Exception {
		Object instance = ContextProviderUtil.getMatchedResource(message);

		if (instance == null) {
			return;
		}

		HttpServletRequest httpServletRequest =
			ContextProviderUtil.getHttpServletRequest(message);

		Class<?> clazz = instance.getClass();

		Class<?> superClass = clazz.getSuperclass();

		for (Field field : superClass.getDeclaredFields()) {
			if (Modifier.isFinal(field.getModifiers()) ||
				Modifier.isStatic(field.getModifiers())) {

				continue;
			}

			Class<?> fieldClass = field.getType();

			if (fieldClass.isAssignableFrom(AcceptLanguage.class)) {
				field.setAccessible(true);

				field.set(
					instance,
					new AcceptLanguageImpl(
						httpServletRequest, _language, _portal));
			}
			else if (fieldClass.isAssignableFrom(Company.class)) {
				field.setAccessible(true);

				field.set(instance, _portal.getCompany(httpServletRequest));
			}
			else if (fieldClass.isAssignableFrom(HttpServletRequest.class)) {
				field.setAccessible(true);

				field.set(instance, httpServletRequest);
			}
			else if (fieldClass.isAssignableFrom(HttpServletResponse.class)) {
				field.setAccessible(true);

				field.set(
					instance, message.getContextualProperty("HTTP.RESPONSE"));
			}
			else if (fieldClass.isAssignableFrom(UriInfo.class)) {
				field.setAccessible(true);

				field.set(instance, new UriInfoImpl(message));
			}
			else if (fieldClass.isAssignableFrom(User.class)) {
				field.setAccessible(true);

				field.set(instance, _portal.getUser(httpServletRequest));
			}
		}
	}

	private final Language _language;
	private final Portal _portal;

}