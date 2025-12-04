/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.jaxrs.writer.interceptor;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.internal.accept.language.AcceptLanguageImpl;
import com.liferay.portal.vulcan.internal.fields.NestedFieldsSetterUtil;
import com.liferay.portal.vulcan.internal.fields.servlet.NestedFieldsHttpServletRequestWrapper;
import com.liferay.portal.vulcan.internal.jaxrs.message.exchange.ExchangeWrapper;
import com.liferay.portal.vulcan.jaxrs.context.ContextDataInjector;
import com.liferay.portal.vulcan.jaxrs.context.ContextDataInjectorBuilderFactory;
import com.liferay.portal.vulcan.util.UriInfoUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.ext.WriterInterceptor;
import jakarta.ws.rs.ext.WriterInterceptorContext;

import java.io.IOException;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.jaxrs.impl.UriInfoImpl;
import org.apache.cxf.jaxrs.provider.ProviderFactory;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;

/**
 * @author Ivica Cardic
 */
@Provider
public class NestedFieldsWriterInterceptor implements WriterInterceptor {

	public NestedFieldsWriterInterceptor(
		ContextDataInjectorBuilderFactory contextDataInjectorBuilderFactory,
		Language language, Portal portal, Object scopeChecker) {

		_contextDataInjectorBuilderFactory = contextDataInjectorBuilderFactory;
		_language = language;
		_portal = portal;
		_scopeChecker = scopeChecker;
	}

	@Override
	public void aroundWriteTo(WriterInterceptorContext writerInterceptorContext)
		throws IOException {

		try {
			NestedFieldsSetterUtil.setNestedFields(
				writerInterceptorContext.getEntity(),
				(fieldName, nestedFieldsContext, resource) -> {
					Message message = nestedFieldsContext.getMessage();

					NestedFieldsHttpServletRequestWrapper httpServletRequest =
						new NestedFieldsHttpServletRequestWrapper(
							fieldName,
							(HttpServletRequest)message.getContextualProperty(
								"HTTP.REQUEST"));

					message.put("HTTP.REQUEST", httpServletRequest);

					message.setExchange(
						new ExchangeWrapper(message.getExchange(), resource));

					return new NestedFieldsSetterUtil.
						NestedFieldsSetterSafeCloseable() {

						@Override
						public void close() {
							NestedFieldsHttpServletRequestWrapper
								nestedFieldsHttpServletRequestWrapper =
									(NestedFieldsHttpServletRequestWrapper)
										message.getContextualProperty(
											"HTTP.REQUEST");

							message.put(
								"HTTP.REQUEST",
								nestedFieldsHttpServletRequestWrapper.
									getRequest());

							Exchange exchange = message.getExchange();

							if (exchange instanceof ExchangeWrapper) {
								ExchangeWrapper exchangeWrapper =
									(ExchangeWrapper)exchange;

								message.setExchange(
									exchangeWrapper.getExchange());
							}
						}

						@Override
						public ContextDataInjector getContextDataInjector()
							throws Exception {

							return _contextDataInjectorBuilderFactory.builder(
							).acceptLanguage(
								new AcceptLanguageImpl(
									httpServletRequest, _language, _portal)
							).company(
								_portal.getCompany(httpServletRequest)
							).fallbackContextValueFunction(
								contextClass -> {
									if (contextClass.equals(UriInfo.class)) {
										return new UriInfoImpl(message);
									}

									ProviderFactory providerFactory =
										ProviderFactory.getInstance(message);

									ContextProvider<?> contextProvider =
										providerFactory.createContextProvider(
											contextClass, message);

									if (contextProvider != null) {
										return contextProvider.createContext(
											message);
									}

									return null;
								}
							).httpServletRequest(
								httpServletRequest
							).httpServletResponse(
								(HttpServletResponse)
									message.getContextualProperty(
										"HTTP.RESPONSE")
							).scopeChecker(
								_scopeChecker
							).uriInfo(
								UriInfoUtil.getVulcanUriInfo(
									httpServletRequest,
									new UriInfoImpl(message))
							).user(
								_portal.getUser(httpServletRequest)
							).build();
						}

					};
				});
		}
		catch (Exception exception) {
			_log.error(exception);

			throw new WebApplicationException(exception);
		}

		writerInterceptorContext.proceed();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NestedFieldsWriterInterceptor.class);

	private final ContextDataInjectorBuilderFactory
		_contextDataInjectorBuilderFactory;
	private final Language _language;
	private final Portal _portal;
	private final Object _scopeChecker;

}