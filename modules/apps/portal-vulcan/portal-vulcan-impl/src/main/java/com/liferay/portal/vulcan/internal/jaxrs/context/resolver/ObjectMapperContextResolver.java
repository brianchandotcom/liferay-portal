/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.jaxrs.context.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.portal.vulcan.jackson.databind.ObjectMapperProviderUtil;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * @author Javier Gamarra
 */
@Provider
public class ObjectMapperContextResolver
	implements ContextResolver<ObjectMapper> {

	@Override
	public ObjectMapper getContext(Class<?> clazz) {
		return ObjectMapperProviderUtil.getObjectMapper();
	}

}