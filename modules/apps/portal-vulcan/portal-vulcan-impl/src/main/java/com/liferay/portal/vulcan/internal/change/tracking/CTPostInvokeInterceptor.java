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

import com.liferay.petra.lang.SafeClosable;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

/**
 * @author Preston Crary
 */
public class CTPostInvokeInterceptor extends AbstractPhaseInterceptor<Message> {

	public static final CTPostInvokeInterceptor INSTANCE =
		new CTPostInvokeInterceptor();

	@Override
	public void handleMessage(Message message) {
		SafeClosable safeClosable = (SafeClosable)message.get(
			CTPreInvokeInterceptor.CT_COLLECTION_SAFE_CLOSABLE);

		if (safeClosable != null) {
			safeClosable.close();
		}
	}

	private CTPostInvokeInterceptor() {
		super(Phase.POST_INVOKE);
	}

}