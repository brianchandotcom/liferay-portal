/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.jaxrs.context.provider;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Providers;

import org.apache.cxf.jaxrs.utils.JAXRSUtils;
import org.apache.cxf.message.Message;

import org.osgi.service.component.annotations.Component;

/**
 * @author Luis Miguel Barcos
 */
@Component(service = APIApplicationProvider.class)
public class APIApplicationProvider {

	public <T> T provide(Class<T> clazz, Message message, Providers providers) {
		ContextResolver<T> contextResolver = providers.getContextResolver(
			clazz,
			JAXRSUtils.toMediaType((String)message.get(Message.CONTENT_TYPE)));

		return contextResolver.getContext(clazz);
	}

}