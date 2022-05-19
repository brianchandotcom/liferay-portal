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

import com.liferay.petra.lang.SafeCloseable;
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

		interceptorChain.add(_CT_PRE_INVOKE_PHASE_INTERCEPTOR);
		interceptorChain.add(_CT_POST_INVOKE_PHASE_INTERCEPTOR);
	}

	private static final String _CT_COLLECTION_SAFE_CLOSABLE =
		CTPreInvokePhaseInterceptor.class.getName() +
			"#CT_COLLECTION_SAFE_CLOSABLE";

	private static final CTPostInvokePhaseInterceptor
		_CT_POST_INVOKE_PHASE_INTERCEPTOR = new CTPostInvokePhaseInterceptor();

	private static final CTPreInvokePhaseInterceptor
		_CT_PRE_INVOKE_PHASE_INTERCEPTOR = new CTPreInvokePhaseInterceptor();

	private static class CTPostInvokePhaseInterceptor
		extends AbstractPhaseInterceptor<Message> {

		@Override
		public void handleMessage(Message message) {
			SafeCloseable safeCloseable = (SafeCloseable)message.get(
				_CT_COLLECTION_SAFE_CLOSABLE);

			if (safeCloseable != null) {
				safeCloseable.close();
			}
		}

		private CTPostInvokePhaseInterceptor() {
			super(Phase.POST_INVOKE);
		}

	}

	private static class CTPreInvokePhaseInterceptor
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
						CTCollectionThreadLocal.
							setProductionModeWithSafeCloseable());
				}
			}
		}

		private CTPreInvokePhaseInterceptor() {
			super(Phase.PRE_INVOKE);
		}

	}

}