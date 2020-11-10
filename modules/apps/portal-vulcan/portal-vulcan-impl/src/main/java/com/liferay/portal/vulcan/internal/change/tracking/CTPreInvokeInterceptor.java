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

package com.liferay.portal.vulcan.internal.change.tracking;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.petra.reflect.AnnotationLocator;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;

import java.lang.reflect.Method;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

/**
 * @author Preston Crary
 */
public class CTPreInvokeInterceptor extends AbstractPhaseInterceptor<Message> {

	public static final String CT_COLLECTION_SAFE_CLOSABLE =
		CTPreInvokeInterceptor.class.getName() + "#CT_COLLECTION_SAFE_CLOSABLE";

	public static final CTPreInvokeInterceptor INSTANCE =
		new CTPreInvokeInterceptor();

	@Override
	public void handleMessage(Message message) {
		Method method = (Method)message.get("org.apache.cxf.resource.method");

		if ((method != null) && !CTCollectionThreadLocal.isProductionMode()) {
			CTAware ctAware = AnnotationLocator.locate(
				method, method.getDeclaringClass(), CTAware.class);

			if ((ctAware == null) || ctAware.onProduction()) {
				message.put(
					CT_COLLECTION_SAFE_CLOSABLE,
					CTCollectionThreadLocal.setCTCollectionId(
						CTConstants.CT_COLLECTION_ID_PRODUCTION));
			}
		}
	}

	private CTPreInvokeInterceptor() {
		super(Phase.PRE_INVOKE);
	}

}