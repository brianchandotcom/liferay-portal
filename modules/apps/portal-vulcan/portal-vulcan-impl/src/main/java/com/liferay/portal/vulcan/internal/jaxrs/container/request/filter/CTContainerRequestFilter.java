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

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.petra.reflect.AnnotationLocator;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;

import java.lang.reflect.Method;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.interceptor.InterceptorChain;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.phase.PhaseInterceptorChain;

/**
 * @author Preston Crary
 */
@Provider
public class CTContainerRequestFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext containerRequestContext) {
		Message message = PhaseInterceptorChain.getCurrentMessage();

		InterceptorChain interceptorChain = message.getInterceptorChain();

		interceptorChain.add(_CT_PRE_INVOKE_INTERCEPTOR);
		interceptorChain.add(_CT_POST_INVOKE_INTERCEPTOR);
	}

	private static final String _CT_COLLECTION_SAFE_CLOSABLE =
		CTPreInvokeInterceptor.class.getName() + "#CT_COLLECTION_SAFE_CLOSABLE";

	private static final CTPostInvokeInterceptor _CT_POST_INVOKE_INTERCEPTOR =
		new CTPostInvokeInterceptor();

	private static final CTPreInvokeInterceptor _CT_PRE_INVOKE_INTERCEPTOR =
		new CTPreInvokeInterceptor();

	private static class CTPostInvokeInterceptor
		extends AbstractPhaseInterceptor<Message> {

		@Override
		public void handleMessage(Message message) {
			SafeClosable safeClosable = (SafeClosable)message.get(
				_CT_COLLECTION_SAFE_CLOSABLE);

			if (safeClosable != null) {
				safeClosable.close();
			}
		}

		private CTPostInvokeInterceptor() {
			super(Phase.POST_INVOKE);
		}

	}

	private static class CTPreInvokeInterceptor
		extends AbstractPhaseInterceptor<Message> {

		@Override
		public void handleMessage(Message message) {
			Method method = (Method)message.get(
				"org.apache.cxf.resource.method");

			if ((method != null) &&
				!CTCollectionThreadLocal.isProductionMode()) {

				CTAware ctAware = AnnotationLocator.locate(
					method, method.getDeclaringClass(), CTAware.class);

				if ((ctAware == null) || ctAware.onProduction()) {
					message.put(
						_CT_COLLECTION_SAFE_CLOSABLE,
						CTCollectionThreadLocal.setCTCollectionId(
							CTConstants.CT_COLLECTION_ID_PRODUCTION));
				}
			}
		}

		private CTPreInvokeInterceptor() {
			super(Phase.PRE_INVOKE);
		}

	}

}