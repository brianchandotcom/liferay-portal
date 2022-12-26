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

package com.liferay.portal.remote.jaxrs.whiteboard.jaxb.json.internal;

import com.fasterxml.jackson.jaxrs.cfg.Annotations;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import org.osgi.framework.Bundle;
import org.osgi.framework.PrototypeServiceFactory;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Shuyang Zhou
 */
public class JacksonJsonProviderPrototypeServiceFactory
	implements PrototypeServiceFactory<JacksonJsonProvider> {

	@Override
	public JacksonJsonProvider getService(
		Bundle bundle,
		ServiceRegistration<JacksonJsonProvider> serviceRegistration) {

		return new JacksonJsonProvider(Annotations.JACKSON, Annotations.JAXB);
	}

	@Override
	public void ungetService(
		Bundle bundle,
		ServiceRegistration<JacksonJsonProvider> serviceRegistration,
		JacksonJsonProvider jacksonJsonProvider) {
	}

}